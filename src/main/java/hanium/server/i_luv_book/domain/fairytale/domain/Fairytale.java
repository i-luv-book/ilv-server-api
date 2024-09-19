package hanium.server.i_luv_book.domain.fairytale.domain;

import hanium.server.i_luv_book.domain.fairytale.domain.enums.FariyTaleDifficulty;
import hanium.server.i_luv_book.domain.fairytale.dto.response.GeneralFairyTaleResponseDTO;
import hanium.server.i_luv_book.domain.user.domain.Child;
import hanium.server.i_luv_book.global.common.basetime.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Young9
 */
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Fairytale extends BaseTimeEntity {

    @Id
    @Column(name = "fairytale_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @Column(name= "summary",length = 1000)
    private String summary;
    private String thumbnail;

    @Column(name = "is_liked")
    private boolean isLiked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id")
    private Child child;

    @OneToMany(mappedBy = "fairytale", cascade = CascadeType.ALL)
    @Builder.Default
    private List<FairytalePage> fairytalePages = new ArrayList<>();

    @OneToMany(mappedBy = "fairytale", cascade = CascadeType.ALL)
    @Builder.Default
    private List<FairytaleKeyword> fairytaleKeywords = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private FariyTaleDifficulty difficulty;

    public void addPage(GeneralFairyTaleResponseDTO.PagesDTO pagesDTO) {
        FairytalePage fairytalePage = FairytalePage.builder()
                .content(pagesDTO.getContent())
                .fairytale(this)
                .imgUrl(pagesDTO.getImgURL())
                .build();

        fairytalePages.add(fairytalePage);
        fairytalePage.setFairytale(this);
    }

    public void setChild(Child child) {
        this.child = child;
    }
}
