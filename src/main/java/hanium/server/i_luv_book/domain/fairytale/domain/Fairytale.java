package hanium.server.i_luv_book.domain.fairytale.domain;

import hanium.server.i_luv_book.domain.fairytale.domain.enums.FariyTaleDifficulty;
import hanium.server.i_luv_book.domain.user.domain.Child;
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
public class Fairytale {

    @Id
    @Column(name = "fairytale_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String summary;
    private String thumbnail;

    @Column(name = "is_liked")
    private boolean isLiked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id")
    private Child child;

    @OneToMany(mappedBy = "fairytale", cascade = CascadeType.ALL)
    private List<FairytalePage> fairytalePages = new ArrayList<>();

    @OneToMany(mappedBy = "fairytale", cascade = CascadeType.ALL)
    private List<FairytaleKeyword> fairytaleKeywords = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private FariyTaleDifficulty difficulty;


}
