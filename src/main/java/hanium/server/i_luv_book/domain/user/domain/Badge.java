package hanium.server.i_luv_book.domain.user.domain;

import hanium.server.i_luv_book.global.common.basetime.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ijin
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "badge_id")
    private long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private BadgeType type;
    @Column(unique = true, name = "img_url")
    @Size(max = 300)
    private String imgUrl;

    @OneToMany(mappedBy = "badge", cascade = CascadeType.ALL)
    private List<ChildBadge> childBadges = new ArrayList<>();

    public void addChildBadge(ChildBadge childBadge) {
        childBadges.add(childBadge);
    }

    @Builder
    public Badge(BadgeType type, String imgUrl) {
        this.type = type;
        this.imgUrl = imgUrl;
    }

    @Getter
    public enum BadgeType {
        // Login
        FIRST_LOGIN,
        SEVEN_DAYS_LOGIN,
        THIRTY_DAYS_LOGIN,

        // Tale
        THIRTY_MINUTES_READ,
        ONE_HOUR_READ,
        CHRISTMAS_TALE,
        FIRST_TALE,
        ACTION_TALE,
        MAGICIAN_TALE,
        SPACE_TALE,
        FANTASY_TALE,
        ARTIST,

        // Education
        THIRTY_MINUTES_SOLVE,
        ONE_HOUR_SOLVE,
        ONE_THOUSAND_VOCABULARY,
        THIRD_MIDTERM,

        // Game
        PONG
    }
}
