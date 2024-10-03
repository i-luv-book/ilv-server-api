package hanium.server.i_luv_book.domain.education.domain;

import hanium.server.i_luv_book.domain.education.application.dto.request.QuizCreateCommand;
import hanium.server.i_luv_book.domain.fairytale.domain.Fairytale;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class QuizTest {

    @Test
    void solveQuiz() {
        // given
        QuizCreateCommand command = new QuizCreateCommand(QuizInfo.QuizType.CREATIVITY, QuizInfo.Format.SHORT_ANSWER, "창의력 문제", null, "창의력 문제입니다.", null);
        Fairytale fairytale = new Fairytale();
        Quiz quiz = Quiz.builder()
                .quizCreateCommand(command)
                .fairytale(fairytale)
                .build();

        // when
        boolean corrected = quiz.solveQuiz("창의력 문제입니다.");

        // then
        Assertions.assertTrue(corrected);
    }
}
