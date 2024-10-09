package hanium.server.i_luv_book.domain.education.application.dto.request;

public record ChildAnswerCommand(Long quizId, String childAnswer, boolean correct) {
}
