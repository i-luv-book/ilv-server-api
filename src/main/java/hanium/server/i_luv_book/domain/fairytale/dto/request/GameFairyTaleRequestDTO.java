package hanium.server.i_luv_book.domain.fairytale.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class GameFairyTaleRequestDTO {
    private KeywordsDTO keywords;
    private String fairytale;
}
