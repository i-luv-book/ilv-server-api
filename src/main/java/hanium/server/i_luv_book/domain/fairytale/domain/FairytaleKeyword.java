package hanium.server.i_luv_book.domain.fairytale.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.security.Key;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FairytaleKeyword {

    @Id
    @Column(name = "fairytale_keyword_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fairytale_id")
    private Fairytale fairytale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_id")
    private Keyword keyword;

}
