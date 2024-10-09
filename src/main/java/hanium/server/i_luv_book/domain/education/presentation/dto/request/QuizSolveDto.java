package hanium.server.i_luv_book.domain.education.presentation.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class QuizSolveDto {
    @NotNull(message = "fairytaleId is null")
    private Long fairytaleId;
    @NotEmpty(message = "childAnswerDtos are empty")
    private List<ChildAnswerDto> childAnswerDtos;

    public QuizSolveDto() {

    }

    public QuizSolveDto(Long fairytaleId, List<ChildAnswerDto> childAnswerDtos) {
        this.fairytaleId = fairytaleId;
        this.childAnswerDtos = childAnswerDtos;
    }
}
