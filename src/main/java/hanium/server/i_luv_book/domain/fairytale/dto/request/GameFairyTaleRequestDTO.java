package hanium.server.i_luv_book.domain.fairytale.dto.request;

import hanium.server.i_luv_book.domain.fairytale.domain.enums.FariyTaleDifficulty;
import lombok.Data;

import java.util.List;

@Data
public class GameFairyTaleRequestDTO {
    private KeywordsDTO keywords;
    private String selection;
    private Long fairytaleId;
    private FariyTaleDifficulty difficulty;
}
