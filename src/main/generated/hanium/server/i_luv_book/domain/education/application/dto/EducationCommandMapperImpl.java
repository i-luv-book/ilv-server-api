package hanium.server.i_luv_book.domain.education.application.dto;

import hanium.server.i_luv_book.domain.education.application.dto.request.QuizCreateCommand;
import hanium.server.i_luv_book.domain.education.domain.Quiz;
import hanium.server.i_luv_book.domain.education.domain.Quiz.QuizBuilder;
import hanium.server.i_luv_book.domain.fairytale.domain.Fairytale;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-09T11:14:40+0900",
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
}
