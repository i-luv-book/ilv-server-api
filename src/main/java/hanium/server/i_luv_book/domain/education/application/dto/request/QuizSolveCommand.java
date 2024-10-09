package hanium.server.i_luv_book.domain.education.application.dto.request;


import java.util.List;

public record QuizSolveCommand(Long fairytaleId, List<ChildAnswerCommand> childAnswerCommands) {
}
