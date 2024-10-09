package hanium.server.i_luv_book.domain.education.application.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

@Getter
public class QuizOptionsInfo {
    @JsonIgnore
    private Long quizId;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;

    public QuizOptionsInfo(Long quizId, String optionA, String optionB, String optionC, String optionD) {
        this.quizId = quizId;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
    }
}
