package hanium.server.i_luv_book.domain.education.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ChildAnswerDto {
    @NotNull(message = "quizId is null")
    private Long quizId;
    private String childAnswer;
    private boolean correct;

    public ChildAnswerDto() {

    }

    public ChildAnswerDto(Long quizId, String childAnswer, boolean correct) {
        this.quizId = quizId;
        this.childAnswer = childAnswer;
        this.correct = correct;
    }
}
