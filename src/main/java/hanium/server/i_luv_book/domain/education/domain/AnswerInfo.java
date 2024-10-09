package hanium.server.i_luv_book.domain.education.domain;

import hanium.server.i_luv_book.domain.education.application.dto.request.QuizCreateCommand;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AnswerInfo {
    @Column(name = "answer")
    private String answer;
    @Column(name = "child_answer")
    private String childAnswer;
    @Column(name = "is_corrected")
    private boolean isCorrect;

    @Builder
    public AnswerInfo(QuizCreateCommand command) {
        this.answer = command.getAnswer();
        this.isCorrect = false;
    }

    public void updateAnswerInfo(String childAnswer, boolean isCorrect) {
        this.childAnswer = childAnswer;
        this.isCorrect = isCorrect;
    }
}
