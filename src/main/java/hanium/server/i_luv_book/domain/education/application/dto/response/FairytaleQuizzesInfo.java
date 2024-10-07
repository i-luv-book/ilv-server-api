package hanium.server.i_luv_book.domain.education.application.dto.response;

import hanium.server.i_luv_book.domain.fairytale.domain.Fairytale;
import lombok.Getter;

@Getter
public class FairytaleQuizzesInfo {
    private long fairytaleId;
    private String title;
    private long correctQuizzesCount;

    public FairytaleQuizzesInfo(long fairytaleId, String title, long correctQuizzesCount, Fairytale.Level level) {
        this.fairytaleId = fairytaleId;
        this.title = title;
        switch (level) {
            case LOW -> this.correctQuizzesCount = correctQuizzesCount + 2;
            case MEDIUM, HIGH -> this.correctQuizzesCount = correctQuizzesCount + 1;
        }
    }
}
