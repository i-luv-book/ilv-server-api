package hanium.server.i_luv_book.domain.fairytale.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FariyTaleDifficulty {

    LOW("https://iluvbook.com/fairytale/low","https://iluvbook.com/fairytale/game","https://iluvbook.com/fairytale/game/end"),
    MEDIUM("https://iluvbook.com/fairytale/mid","https://iluvbook.com/fairytale/game","https://iluvbook.com/fairytale/game/end"),
    HIGH("https://iluvbook.com/fairytale/high","https://iluvbook.com/fairytale/game","https://iluvbook.com/fairytale/game/end");

    private final String generalUrl;
    private final String gameUrl;
    private final String gameEndUrl;
}
