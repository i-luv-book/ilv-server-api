package hanium.server.i_luv_book.domain.fairytale.dto.response;

import lombok.Data;
/**
 * @author Young9
 */
@Data
public class GameFairyTaleEndResponseDTO implements GameFairyTaleResponseDTO{
    private String title;
    private String content;
    private String imgURL;
}
