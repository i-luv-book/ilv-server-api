package hanium.server.i_luv_book.domain.fairytale.dto.response;

import lombok.Data;

@Data
public class GameFairyTaleEndResponseDTO implements GameFairyTaleResponseDTO{
    private String title;
    private String content;
    private String imgURL;
}
