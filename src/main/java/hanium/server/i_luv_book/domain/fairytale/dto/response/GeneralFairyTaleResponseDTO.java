package hanium.server.i_luv_book.domain.fairytale.dto.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Young9
 */
@Data
public class GeneralFairyTaleResponseDTO {
    private String title;
    private List<PagesDTO> pages = new ArrayList<>();
    private String summary;

    @Data
    static public class PagesDTO {
        private String content;
        private String imgURL;
    }

}

