package hanium.server.i_luv_book.domain.fairytale.domain;

import hanium.server.i_luv_book.domain.fairytale.domain.enums.KeywordCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Keyword {

    @Id
    @Column(name = "keyword_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @Enumerated(EnumType.STRING)
    private KeywordCategory keywordCategory;

    @OneToMany(mappedBy = "keyword", cascade = CascadeType.ALL)
    private List<FairytaleKeyword> fairytaleKeywords = new ArrayList<>();

}
