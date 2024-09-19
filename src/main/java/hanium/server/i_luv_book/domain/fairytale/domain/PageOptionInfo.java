package hanium.server.i_luv_book.domain.fairytale.domain;

import hanium.server.i_luv_book.domain.fairytale.domain.enums.Selection;
import hanium.server.i_luv_book.global.common.basetime.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
/**
 * @author Young9
 */
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PageOptionInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="page_option_info_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name="selection")
    private Selection selection;

    @Column(name = "title_a")
    private String titleA;
    @Column(name = "content_a")
    private String contentA;
    @Column(name = "title_b")
    private String titleB;
    @Column(name = "content_b")
    private String contentB;
    @Column(name = "title_c")
    private String titleC;
    @Column(name = "content_c")
    private String contentC;



    @OneToOne
    @JoinColumn(name = "fairytale_page_id")
    private FairytalePage fairytalePage;
}
