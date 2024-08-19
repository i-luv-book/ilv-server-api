package hanium.server.i_luv_book.user;

import hanium.server.i_luv_book.common.basetime.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ijin
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Child extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "child_id")
    private long id;
    @Column(unique = true, name = "nickname")
    private String nickname;
    @Column(name = "age")
    private int age;
    @Column(name = "gender")
    private Gender gender;
    @Column(name = "img_url")
    private String imgUrl;
    @Embedded
    private LoginStatus loginStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Parent parent;
    @OneToMany(mappedBy = "child", cascade = CascadeType.ALL)
    private List<ChildBadge> childBadges = new ArrayList<>();

    @Getter
    @RequiredArgsConstructor
    public enum Gender {
        MALE, FEMALE
    }
}
