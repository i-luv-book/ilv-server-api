package hanium.server.i_luv_book.domain.fairytale.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class GeneralFairyTaleResponseDTO {
    private String title;
    private List<PagesDTO> pages;
    private String summary;

    @Data
    public class PagesDTO {
        private String content;
        private String imgURL;
    }

}

