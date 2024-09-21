package hanium.server.i_luv_book.domain.user.domain;

import hanium.server.i_luv_book.domain.fairytale.domain.Fairytale;
import hanium.server.i_luv_book.domain.user.application.dto.request.ChildCreateCommand;
import hanium.server.i_luv_book.global.common.basetime.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static hanium.server.i_luv_book.domain.user.domain.Badge.*;

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
    @Embedded
    private ProfileImage profileImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Parent parent;
    @OneToMany(mappedBy = "child", cascade = CascadeType.ALL)
    private List<ChildBadge> childBadges = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "activity_info_id")
    private ActivityInfo activityInfo;

    @OneToMany(mappedBy = "child", cascade = CascadeType.ALL)
    private List<Fairytale> fairytales = new ArrayList<>();

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
        this.parent = parent;
        this.profileImage = new ProfileImage();
        this.activityInfo = new ActivityInfo();
    }

    public void registerParent(Parent parent) {
        this.parent = parent;
    }

    public void putProfileImage(String imageUrl) {
        this.profileImage = ProfileImage.builder()
                .imageUrl(imageUrl)
                .build();
    }

    public String getProfileImageUrl() {
        return profileImage.getImageUrl();
    }

    public void addChildBadge(ChildBadge childBadge) {
        childBadges.add(childBadge);
    }

    public void addFairytale(Fairytale fairytale) {
        fairytales.add(fairytale);
        fairytale.setChild(this);
    }

    public List<BadgeType> updateFairytaleReadingInfo ( int minute){
        return activityInfo.updateFairytaleReadingDuration(minute);
    }

    public List<BadgeType> updateQuizSolvingInfo ( int minute){
        return activityInfo.updateQuizSolvingDuration(minute);
    }

    public BadgeType updateLoginDateInfo () {
        return activityInfo.updateLoginStatus();
    }

}
