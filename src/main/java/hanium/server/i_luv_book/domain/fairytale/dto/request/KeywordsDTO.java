package hanium.server.i_luv_book.domain.fairytale.dto.request;


import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
/**
 * @author Young9
 */
@Data
public class KeywordsDTO {
    @Size(min =1, max = 2,message = "인물 키워드는 1개부터 2개까지 허용합니다. ")
    private List<String> traits;

    @Size(min =1, max = 2,message = "이름 키워드는 1개부터 2개까지 허용합니다. ")
    private List<String> characters;

    @Size(min =1, max = 2,message = "배경 키워드는 1개부터 2개까지 허용합니다. ")
    private List<String> settings;

    @Size(min =1, max = 1,message = "장르 키워드는 1개만 허용합니다. ")
    private List<String> genre;
}
