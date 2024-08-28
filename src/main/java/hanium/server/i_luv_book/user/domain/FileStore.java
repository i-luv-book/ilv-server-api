package hanium.server.i_luv_book.user.domain;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author ijin
 */
public interface FileStore {

    String upload(MultipartFile file);

    void delete(String imageUrl);
}

