package hanium.server.i_luv_book.domain.user.infra;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import hanium.server.i_luv_book.global.exception.FileIoException;
import hanium.server.i_luv_book.global.exception.code.ErrorCode;
import hanium.server.i_luv_book.domain.user.domain.FileStore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author ijin
 */
@Component
@RequiredArgsConstructor
public class S3FileStore implements FileStore {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

    private static final List<String> ALLOWED_FILE_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif");

    @Override
    public String upload(MultipartFile file) {
        validateFile(file);
        return uploadImage(file);
    }

    @Override
    public void delete(String imageUrl) {
        String key = getKeyFromImageAddress(imageUrl);
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucketName, key));
    }

    // Validate Files
    private void validateFile(MultipartFile file) {
        validateFileEmpty(file);

        String fileName = file.getOriginalFilename();
        validateFileExtension(fileName);
    }

    private void validateFileEmpty(MultipartFile file) {
        if(file.isEmpty() || Objects.isNull(file.getOriginalFilename())){
            throw new FileIoException(ErrorCode.EMPTY_FILE);
        }
    }

    private void validateFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex == -1) {
            throw new FileIoException(ErrorCode.EMPTY_EXTENSION);
        }

        String extension = fileName.substring(lastDotIndex + 1).toLowerCase();
        if(!ALLOWED_FILE_EXTENSIONS.contains(extension)) {
            throw new FileIoException(ErrorCode.INVALID_EXTENSION);
        }
    }

    // Upload to S3
    private String uploadImage(MultipartFile file) {
        try {
            return uploadFileToS3(file);
        } catch (IOException e) {
            throw new FileIoException(ErrorCode.FAILED_IO);
        }
    }

    private String uploadFileToS3(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf("."));

        // Processed File Name to save
        String s3FileName = UUID.randomUUID().toString().substring(0, 10) + fileName;

        // Save & Put to S3
        try{
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            amazonS3Client.putObject(bucketName, fileName, file.getInputStream(), metadata);
        }catch (Exception e){
            throw new FileIoException(ErrorCode.FAILED_PUT_OBJECT);
        }

        // Saved File's Url
        return amazonS3Client.getUrl(bucketName, s3FileName).toString();
    }

    // Get Storage's Key
    private String getKeyFromImageAddress(String imageAddress){
        try{
            URL url = new URL(imageAddress);
            String decodingKey = URLDecoder.decode(url.getPath(), "UTF-8");
            return decodingKey.substring(1);
        }catch (MalformedURLException | UnsupportedEncodingException e){
            throw new FileIoException(ErrorCode.FAILED_DELETE_OBJECT);
        }
    }
}
