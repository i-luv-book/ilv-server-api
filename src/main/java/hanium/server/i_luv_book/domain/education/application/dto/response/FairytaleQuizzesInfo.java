package hanium.server.i_luv_book.domain.education.application.dto.response;

import hanium.server.i_luv_book.domain.fairytale.domain.Fairytale;
import lombok.Getter;

@Getter
public class FairytaleQuizzesInfo {
    private Long fairytaleId;
    private String title;
    private Long correctQuizzesCount;

    public FairytaleQuizzesInfo(Long fairytaleId, String title, Long correctQuizzesCount, Fairytale.Level level) {
        this.fairytaleId = fairytaleId;
        this.title = title;
        switch (level) {
            case LOW -> this.correctQuizzesCount = correctQuizzesCount + 2;
            case MEDIUM, HIGH -> this.correctQuizzesCount = correctQuizzesCount + 1;
        }
    }
}
