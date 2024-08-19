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
public class Parent extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parent_id")
    private long id;
    @Column(unique = true, name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "is_notified")
    private boolean isNotified;
    @Column(name = "membership_type")
    private MembershipType membershipType;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Child> diaries = new ArrayList<>();

    @Getter
    @RequiredArgsConstructor
    public enum MembershipType {
        PAID, FREE
    }
}
