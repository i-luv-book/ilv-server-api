package hanium.server.i_luv_book.domain.education.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Queue;

import static hanium.server.i_luv_book.domain.education.domain.QuizInfo.*;

@Data
@AllArgsConstructor
public class QuizCreateCommand {
    private QuizType quizType;
    private Format format;
    private String question;
    private String pronounOrWord;
    private String answer;
    private QuizOptionsCreateCommand quizOptionsInfo;

    @Data
    public static class QuizOptionsCreateCommand {
        private String optionA;
        private String optionB;
        private String optionC;
        private String optionD;

        public QuizOptionsCreateCommand(Queue<String> options) {
            this.optionA = options.remove();
            this.optionB = options.remove();
            this.optionC = options.remove();
            this.optionD = options.remove();
        }
    }
}
