package hanium.server.i_luv_book.domain.fairytale.dto.request;

import hanium.server.i_luv_book.domain.fairytale.domain.enums.FariyTaleDifficulty;
import lombok.Data;

@Data
public class GeneralFairyTaleRequestDTO {
    private KeywordsDTO keywords;
    private FariyTaleDifficulty difficulty;
}
