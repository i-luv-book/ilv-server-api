package hanium.server.i_luv_book.domain.education;

import hanium.server.i_luv_book.domain.fairytale.domain.Fairytale;
import jakarta.persistence.*;
import lombok.AccessLevel;
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
    @Column(name = "question")
    private String question;
    @Column(name = "pronoun_or_word")
    private String pronounOrWord;
    @Column(name = "answer")
    private String answer;
    @Column(name = "is_corrected")
    private boolean isCorrect;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fairytale_id")
    private Fairytale fairytale;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "quiz_option_info_id")
    private QuizOptionInfo quizOptionInfo;
}
