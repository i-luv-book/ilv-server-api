package hanium.server.i_luv_book.domain.education.application.dto.response;

import lombok.Data;

import static hanium.server.i_luv_book.domain.education.domain.QuizInfo.*;

@Data
public class QuizDetailInfo {
    private Long quizId;
    private String quizType;
    private String format;
    private String question;
    private String pronounOrVoca;
    private QuizOptionsInfo quizOptions;
    private QuizAnswerInfo quizAnswerInfo;

    public QuizDetailInfo(Long quizId, QuizType quizType, Format format, String question,
                          String pronounOrVoca, String answer, String childAnswer, boolean correct) {
        this.quizId = quizId;
        this.quizType = quizType.name();
        this.format = format.name();
        this.question = question;
        this.pronounOrVoca = pronounOrVoca;
        this.quizAnswerInfo = new QuizAnswerInfo(answer, childAnswer, correct);
    }
}
