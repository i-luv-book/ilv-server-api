package hanium.server.i_luv_book.domain.fairytale.domain;

import hanium.server.i_luv_book.domain.education.Quiz;
import hanium.server.i_luv_book.domain.education.Words;
import hanium.server.i_luv_book.domain.user.domain.Child;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Fairytale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fairytale_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id")
    private Child child;
    @OneToMany(mappedBy = "fairytale", cascade = CascadeType.ALL)
    private List<Quiz> quizzes = new ArrayList<>();
    @OneToMany(mappedBy = "fairytale", cascade = CascadeType.ALL)
    private List<Words> words = new ArrayList<>();
}
