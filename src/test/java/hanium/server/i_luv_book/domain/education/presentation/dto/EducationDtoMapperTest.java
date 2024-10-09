package hanium.server.i_luv_book.domain.education.presentation.dto;

import hanium.server.i_luv_book.domain.education.application.dto.request.QuizSolveCommand;
import hanium.server.i_luv_book.domain.education.presentation.dto.request.ChildAnswerDto;
import hanium.server.i_luv_book.domain.education.presentation.dto.request.QuizSolveDto;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EducationDtoMapperTest {

    @Test
    void toCommand() {
        // given
        List<ChildAnswerDto> childAnswerDtos = new ArrayList<>();
        ChildAnswerDto childAnswerDto1 = new ChildAnswerDto(1L, "답1", true);
        ChildAnswerDto childAnswerDto2 = new ChildAnswerDto(2L, "답2", false);
        childAnswerDtos.add(childAnswerDto1);
        childAnswerDtos.add(childAnswerDto2);

        QuizSolveDto quizSolveDto = new QuizSolveDto(1L, childAnswerDtos);

        // when
        QuizSolveCommand quizSolveCommand = EducationDtoMapper.INSTANCE.toCommand(quizSolveDto);

        // then
        assertTrue(quizSolveCommand.childAnswerCommands().get(0).correct());
        assertFalse(quizSolveCommand.childAnswerCommands().get(1).correct());
    }
}
