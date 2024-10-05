package hanium.server.i_luv_book.domain.fairytale.domain;

import hanium.server.i_luv_book.domain.education.domain.Quiz;
import hanium.server.i_luv_book.domain.education.domain.Words;
import hanium.server.i_luv_book.domain.user.domain.Child;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Fairytale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fairytale_id")
    private long id;
    @Column(name = "quiz_existence")
    private boolean quizzesExistence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id")
    private Child child;
    @OneToMany(mappedBy = "fairytale", cascade = CascadeType.ALL)
    private List<Quiz> quizzes = new ArrayList<>();
    @OneToMany(mappedBy = "fairytale", cascade = CascadeType.ALL)
    private List<Words> words = new ArrayList<>();

    @Builder
    public Fairytale() {
        this.quizzesExistence = false;
    }

    public void addQuizzes(List<Quiz> quizzes) {
        this.quizzes.addAll(quizzes);
    }

    public void addWords(List<Words> words) {
        this.words.addAll(words);
    }

    public void updateQuizzesExistence() {
        this.quizzesExistence = true;
    }
}
