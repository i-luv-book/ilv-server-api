package hanium.server.i_luv_book.domain.education.application.dto;

import hanium.server.i_luv_book.domain.education.application.dto.request.QuizCreateCommand;
import hanium.server.i_luv_book.domain.education.application.dto.request.WordCreateCommand;
import hanium.server.i_luv_book.domain.education.domain.Quiz;
import hanium.server.i_luv_book.domain.education.domain.Words;
import hanium.server.i_luv_book.domain.fairytale.domain.Fairytale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface EducationCommandMapper {
    EducationCommandMapper INSTANCE = Mappers.getMapper(EducationCommandMapper.class);

    Quiz toQuiz(QuizCreateCommand quizCreateCommand, Fairytale fairytale);

    @Mapping(source = "wordCreateCommand.voca", target = "word")
    Words toWord(WordCreateCommand wordCreateCommand, Fairytale fairytale);

    default List<Quiz> toQuizzes(List<QuizCreateCommand> quizCreateCommands, Fairytale fairytale) {
        List<Quiz> quizzes = new ArrayList<>();
        for (QuizCreateCommand quizCreateCommand : quizCreateCommands) {
            Quiz quiz = toQuiz(quizCreateCommand, fairytale);
            quizzes.add(quiz);
        }
        return quizzes;
    }

    default List<Words> toWords(List<WordCreateCommand> wordCreateCommands, Fairytale fairytale) {
        List<Words> words = new ArrayList<>();
        for (WordCreateCommand wordCreateCommand : wordCreateCommands) {
            Words word = toWord(wordCreateCommand, fairytale);
            words.add(word);
        }
        return words;
    }
}
