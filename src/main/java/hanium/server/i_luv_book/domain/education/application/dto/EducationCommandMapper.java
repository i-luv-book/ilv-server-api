package hanium.server.i_luv_book.domain.education.application.dto;

import hanium.server.i_luv_book.domain.education.application.dto.request.QuizCreateCommand;
import hanium.server.i_luv_book.domain.education.domain.Quiz;
import hanium.server.i_luv_book.domain.fairytale.domain.Fairytale;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface EducationCommandMapper {
    EducationCommandMapper INSTANCE = Mappers.getMapper(EducationCommandMapper.class);

    Quiz toQuiz(QuizCreateCommand quizCreateCommand, Fairytale fairytale);

    default List<Quiz> toQuizzes(List<QuizCreateCommand> quizCreateCommands, Fairytale fairytale) {
        List<Quiz> quizzes = new ArrayList<>();
        for (QuizCreateCommand quizCreateCommand : quizCreateCommands) {
            Quiz quiz = toQuiz(quizCreateCommand, fairytale);
            quizzes.add(quiz);
        }
        return quizzes;
    }
}
