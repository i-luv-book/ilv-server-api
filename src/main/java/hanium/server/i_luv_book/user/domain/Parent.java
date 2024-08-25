package hanium.server.i_luv_book.user.domain;

import hanium.server.i_luv_book.common.basetime.BaseTimeEntity;
import hanium.server.i_luv_book.user.application.dto.request.ParentCreateCommand;
import jakarta.persistence.*;
import lombok.*;

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
    @Column(name = "membership_type")
    private MembershipType membershipType;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Child> children = new ArrayList<>();

    @Getter
    @RequiredArgsConstructor
    public enum MembershipType {
        FREE,             // 무료 회원
        PAID_BASIC,       // 유료 회원(1) - 자녀 3명 추가 가능
        PAID_STANDARD,    // 유료 회원(2) - 자녀 5명 추가 가능
        PAID_PREMIUM      // 유료 회원(3) - 자녀 무제한 추가 가능
    }

    @Builder
    public Parent(ParentCreateCommand parentCreateCommand) {
        this.email = parentCreateCommand.name();
        this.password = parentCreateCommand.password();
    }

    public void addChild(Child child) {
        children.add(child);
    }

    public boolean canAddChild(int currentNumberOfChildren) {
        return switch (membershipType) {
            case FREE -> {
                yield currentNumberOfChildren < 1;
            }
            case PAID_BASIC -> {
                yield currentNumberOfChildren < 3;
            }
            case PAID_STANDARD -> {
                yield currentNumberOfChildren < 5;
            }
            case PAID_PREMIUM -> {
                yield true;
            }
        };
    }
}
