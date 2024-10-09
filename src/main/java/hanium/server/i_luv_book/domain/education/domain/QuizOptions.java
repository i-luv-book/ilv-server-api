package hanium.server.i_luv_book.domain.education.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static hanium.server.i_luv_book.domain.education.application.dto.request.QuizCreateCommand.*;

/**
 * @author ijin
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class QuizOptions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_options_id")
    private long id;
    @Column(name = "option_a")
    private String optionA;
    @Column(name = "option_b")
    private String optionB;
    @Column(name = "option_c")
    private String optionC;
    @Column(name = "option_d")
    private String optionD;

    @Builder
    public QuizOptions(QuizOptionsCreateCommand command) {
        this.optionA = command.getOptionA();
        this.optionB = command.getOptionB();
        this.optionC = command.getOptionC();
        this.optionD = command.getOptionD();
    }
}
