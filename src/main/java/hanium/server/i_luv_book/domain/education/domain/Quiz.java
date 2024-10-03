package hanium.server.i_luv_book.domain.education.domain;

import hanium.server.i_luv_book.domain.education.application.dto.request.QuizCreateCommand;
import hanium.server.i_luv_book.domain.fairytale.domain.Fairytale;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author ijin
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private long id;
    @Embedded
    private QuizInfo quizInfo;
    @Embedded
    private AnswerInfo answerInfo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "quiz_options_id")
    private QuizOptions options;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fairytale_id")
    private Fairytale fairytale;

    @Builder
    public Quiz(QuizCreateCommand quizCreateCommand, Fairytale fairytale) {
        this.quizInfo = QuizInfo.builder().command(quizCreateCommand).build();
        this.answerInfo = AnswerInfo.builder().command(quizCreateCommand).build();
        this.options = (quizCreateCommand.getQuizOptionsInfo() != null) ?
                QuizOptions.builder().command(quizCreateCommand.getQuizOptionsInfo()).build() :
                null;
        this.fairytale = fairytale;
    }

    public boolean solveQuiz(String childAnswer) {
        return answerInfo.updateAnswerInfo(childAnswer);
    }
}
