package hanium.server.i_luv_book.domain.user.domain;

import hanium.server.i_luv_book.global.common.basetime.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ijin
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Badge extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "badge_id")
    private long id;
    @Column(unique = true, name = "name")
    private String name;
    @Column(unique = true, name = "img_url")
    private String imgUrl;

    @OneToMany(mappedBy = "badge", cascade = CascadeType.ALL)
    private List<ChildBadge> childBadges = new ArrayList<>();

    public void addChildBadge(ChildBadge childBadge) {
        childBadges.add(childBadge);
    }
}
