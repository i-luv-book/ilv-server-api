package hanium.server.i_luv_book.domain.education.application.dto;

import hanium.server.i_luv_book.domain.education.application.dto.request.QuizCreateCommand;
import hanium.server.i_luv_book.domain.education.domain.Quiz;
import hanium.server.i_luv_book.domain.fairytale.domain.Fairytale;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static hanium.server.i_luv_book.domain.education.application.dto.request.QuizCreateCommand.*;
import static hanium.server.i_luv_book.domain.education.domain.QuizInfo.*;
import static org.junit.jupiter.api.Assertions.*;

class EducationCommandMapperTest {

    @Test
    @DisplayName("주관식, O/X 퀴즈")
    void toQuiz1() {
        // given
        QuizCreateCommand command = new QuizCreateCommand(QuizType.CREATIVITY, Format.SHORT_ANSWER, "질문", "발음", "답", null);
        Fairytale fairytale = new Fairytale(Fairytale.Level.LOW, "제목");

        // when
        Quiz quiz = Quiz.builder()
                .quizCreateCommand(command)
                .fairytale(fairytale)
                .build();

        // then
        assertEquals("질문", quiz.getQuizInfo().getQuestion());
    }

    @Test
    @DisplayName("객관식 퀴즈")
    void toQuiz2() {
        // given
        Queue<String> options = new LinkedList<>();
        options.add("옵션1");
        options.add("옵션2");
        options.add("옵션3");
        options.add("옵션4");
        QuizOptionsCreateCommand quizOptionsCreateCommand = new QuizOptionsCreateCommand(options);
        QuizCreateCommand quizCreateCommand = new QuizCreateCommand(QuizType.CREATIVITY, Format.SHORT_ANSWER, "질문", "발음", "답", quizOptionsCreateCommand);
        Fairytale fairytale = new Fairytale(Fairytale.Level.LOW, "제목");

        // when
        Quiz quiz = Quiz.builder()
                .quizCreateCommand(quizCreateCommand)
                .fairytale(fairytale)
                .build();

        // then
        assertEquals("질문", quiz.getQuizInfo().getQuestion());
        assertEquals("옵션1", quiz.getOptions().getOptionA());
    }

    @Test
    @DisplayName("퀴즈 한 번에 매핑되는지 테스트")
    void toQuizzes() {
        // given
        List<QuizCreateCommand> commands = new ArrayList<>();
        QuizCreateCommand command1 = new QuizCreateCommand(QuizType.CREATIVITY, Format.SHORT_ANSWER, "질문", "발음", "답", null);
        Queue<String> options = new LinkedList<>();
        options.add("옵션1");
        options.add("옵션2");
        options.add("옵션3");
        options.add("옵션4");
        QuizOptionsCreateCommand quizOptionsCreateCommand = new QuizOptionsCreateCommand(options);
        QuizCreateCommand command2 = new QuizCreateCommand(QuizType.CREATIVITY, Format.SHORT_ANSWER, "질문", "발음", "답", quizOptionsCreateCommand);
        commands.add(command1);
        commands.add(command2);
        Fairytale fairytale = new Fairytale(Fairytale.Level.LOW, "제목");

        // when
        List<Quiz> quizzes = EducationCommandMapper.INSTANCE.toQuizzes(commands, fairytale);

        // then
        assertEquals(2, quizzes.size());
    }
}
