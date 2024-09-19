package hanium.server.i_luv_book.domain.fairytale.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FairytalePage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fairytale_page_id")
    private Long id;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name= "content",length = 10000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fairytale_id")
    private Fairytale fairytale;

    @OneToOne(mappedBy = "fairytalePage", cascade = CascadeType.ALL)
    private PageOptionInfo pageOptionInfo;

    public void setFairytale(Fairytale fairytale) {
        this.fairytale = fairytale;
    }

}
