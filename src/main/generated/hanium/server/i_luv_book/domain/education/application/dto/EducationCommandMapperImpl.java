package hanium.server.i_luv_book.domain.education.application.dto;

import hanium.server.i_luv_book.domain.education.application.dto.request.QuizCreateCommand;
import hanium.server.i_luv_book.domain.education.application.dto.request.WordCreateCommand;
import hanium.server.i_luv_book.domain.education.domain.Quiz;
import hanium.server.i_luv_book.domain.education.domain.Quiz.QuizBuilder;
import hanium.server.i_luv_book.domain.education.domain.Words;
import hanium.server.i_luv_book.domain.education.domain.Words.WordsBuilder;
import hanium.server.i_luv_book.domain.fairytale.domain.Fairytale;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-11T00:16:03+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.11 (Amazon.com Inc.)"
)
@Component
public class EducationCommandMapperImpl implements EducationCommandMapper {

    @Override
    public Quiz toQuiz(QuizCreateCommand quizCreateCommand, Fairytale fairytale) {
        if ( quizCreateCommand == null && fairytale == null ) {
            return null;
        }

        QuizBuilder quiz = Quiz.builder();

        if ( quizCreateCommand != null ) {
            quiz.quizCreateCommand( quizCreateCommand );
        }
        if ( fairytale != null ) {
            quiz.fairytale( fairytale );
        }

        return quiz.build();
    }

    @Override
    public Words toWord(WordCreateCommand wordCreateCommand, Fairytale fairytale) {
        if ( wordCreateCommand == null && fairytale == null ) {
            return null;
        }

        WordsBuilder words = Words.builder();

        if ( wordCreateCommand != null ) {
            words.word( wordCreateCommand.getVoca() );
            words.translation( wordCreateCommand.getTranslation() );
        }
        if ( fairytale != null ) {
            words.fairytale( fairytale );
        }

        return words.build();
    }
}
