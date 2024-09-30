package hanium.server.i_luv_book.domain.education;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class QuizInfo {
    @Enumerated(EnumType.STRING)
    @Column(name = "quiz_type")
    private QuizType quizType;
    @Enumerated(EnumType.STRING)
    @Column(name = "format")
    private Format format;

    @Getter
    public enum QuizType {
        READING, VOCA, LISTENING, GRAMMAR, CREATIVITY
    }

    @Getter
    public enum Format {
        OX, MULTIPLE_CHOICE, SHORT_ANSWER, FREE
    }
}
