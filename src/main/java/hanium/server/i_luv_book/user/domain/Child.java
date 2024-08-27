package hanium.server.i_luv_book.user.domain;

import hanium.server.i_luv_book.common.basetime.BaseTimeEntity;
import hanium.server.i_luv_book.user.application.dto.request.ChildCreateCommand;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "birth_date")
    private LocalDate birthDate;
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

    @Builder
    public Child(ChildCreateCommand childCreateCommand, Parent parent) {
        this.nickname = childCreateCommand.nickname();
        this.birthDate = childCreateCommand.birthDate();
        this.gender = childCreateCommand.gender();
        this.imgUrl = childCreateCommand.imgUrl() == null ? null : childCreateCommand.imgUrl();
        this.loginStatus = new LoginStatus();
        this.parent = parent;
    }

    public void registerParent(Parent parent) {
        this.parent = parent;
    }
}
