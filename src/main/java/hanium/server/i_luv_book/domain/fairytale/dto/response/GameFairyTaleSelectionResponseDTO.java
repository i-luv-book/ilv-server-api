package hanium.server.i_luv_book.domain.fairytale.dto.response;

import lombok.Data;

import java.util.List;
/**
 * @author Young9
 */
@Data
public class GameFairyTaleSelectionResponseDTO implements GameFairyTaleResponseDTO {
    private String title;
    private String content;
    private String imgURL;
    private List<Options> options;


    @Data
    public static class Options {
        private String optionTitle;
        private String optionContent;
    }
}
