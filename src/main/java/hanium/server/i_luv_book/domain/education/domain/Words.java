package hanium.server.i_luv_book.domain.education.domain;

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
public class Words {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "word_id")
    private long id;
    @Column(name = "word")
    private String word;
    @Column(name = "translation")
    private String translation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fairytale_id")
    private Fairytale fairytale;

    @Builder
    public Words(String word, String translation, Fairytale fairytale) {
        this.word = word;
        this.translation = translation;
        this.fairytale = fairytale;
    }
}
