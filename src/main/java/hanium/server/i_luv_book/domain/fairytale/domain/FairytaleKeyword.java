package hanium.server.i_luv_book.domain.fairytale.domain;

import hanium.server.i_luv_book.global.common.basetime.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.security.Key;
/**
 * @author Young9
 */
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FairytaleKeyword extends BaseTimeEntity {

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

    @Column(name = "child_id")
    private Long childId;
}
