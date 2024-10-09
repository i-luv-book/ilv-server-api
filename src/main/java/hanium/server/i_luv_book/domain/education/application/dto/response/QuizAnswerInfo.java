package hanium.server.i_luv_book.domain.education.application.dto.response;

import lombok.Getter;

@Getter
public class QuizAnswerInfo {
    private String answer;
    private String childAnswer;
    private boolean correct;

    public QuizAnswerInfo(String answer, String childAnswer, boolean correct) {
        this.answer = answer;
        this.childAnswer = childAnswer;
        this.correct = correct;
    }

    public QuizAnswerInfo(String answer) {
        this.answer = answer;
    }
}
