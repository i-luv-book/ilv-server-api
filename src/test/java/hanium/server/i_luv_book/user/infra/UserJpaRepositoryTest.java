package hanium.server.i_luv_book.user.infra;

import hanium.server.i_luv_book.domain.auth.domain.LoginType;
import hanium.server.i_luv_book.domain.user.application.dto.request.ChildCreateCommand;
import hanium.server.i_luv_book.domain.user.domain.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class UserJpaRepositoryTest {

    @Autowired
    private UserRepository userJpaRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("부모 저장 및 조회 테스트")
    void saveParent() {
        // given
        Parent parent = Parent.builder()
                .socialId("소셜ID")
                .email("EMAIL")
                .loginType(LoginType.GOOGLE)
                .membershipType(Parent.MembershipType.FREE)
                .role(Role.ROLE_FREE)
                .build();

        // when
        Long savedParentId = userJpaRepository.save(parent);
        Parent savedParent = userJpaRepository.findParentById(savedParentId).get();

        // Then
        assertEquals(parent.getEmail(), savedParent.getEmail());
    }

    @Test
    @DisplayName("자식 저장 및 부모의 자식 조회 테스트")
    void saveChildAndCount() {
        // given
        Parent parent = Parent.builder()
                .socialId("소셜ID")
                .email("EMAIL")
                .loginType(LoginType.GOOGLE)
                .membershipType(Parent.MembershipType.FREE)
                .role(Role.ROLE_FREE)
                .build();
        Long savedParentId = userJpaRepository.save(parent);

        ChildCreateCommand childCreateCommand1 = new ChildCreateCommand("자식1", LocalDate.now(), Child.Gender.MALE,  savedParentId);
        Child child1 = Child.builder().childCreateCommand(childCreateCommand1).build();
        ChildCreateCommand childCreateCommand2 = new ChildCreateCommand("자식2", LocalDate.now(), Child.Gender.MALE, savedParentId);
        Child child2 = Child.builder().childCreateCommand(childCreateCommand2).build();

        // when
        parent.addChild(child1);
        parent.addChild(child2);
        child1.registerParent(parent);
        child2.registerParent(parent);

        // then
        userJpaRepository.save(child1);
        userJpaRepository.save(child2);

        int childCount = userJpaRepository.countChildrenByParentId(savedParentId);
        assertEquals(2, childCount);
    }


    @Test
    @DisplayName("자식 삭제 후 조회 시 예외 발생 테스트")
    void deleteChild_thenThrowExceptionWhenFetching() {
        // Given
        Parent parent = Parent.builder()
                .socialId("소셜ID")
                .email("EMAIL")
                .loginType(LoginType.GOOGLE)
                .membershipType(Parent.MembershipType.FREE)
                .role(Role.ROLE_FREE)
                .build();
        Long savedParentId = userJpaRepository.save(parent);

        ChildCreateCommand childCreateCommand = new ChildCreateCommand("자식1", LocalDate.now(), Child.Gender.MALE, savedParentId);
        Child child = Child.builder().childCreateCommand(childCreateCommand).parent(parent).build();
        Long savedChildId = userJpaRepository.save(child);

        // When
        userJpaRepository.deleteChild(savedParentId, "자식1");
        em.flush();
        em.clear();

        // Then
        assertThrows(NoResultException.class, () -> {
            userJpaRepository.findChildById(savedChildId).orElseThrow(NoResultException::new);
        });
    }

    @Test
    @DisplayName("소셜ID, 소셜 타입으로 부모계정 찾기 테스트")
    void findParentBySocialInfo() {
        // when
        Parent parent = Parent.builder()
                .socialId("SocialID")
                .email("EMAIL")
                .loginType(LoginType.GOOGLE)
                .membershipType(Parent.MembershipType.FREE)
                .role(Role.ROLE_FREE)
                .build();
        Long savedParentId = userJpaRepository.save(parent);

        // given
        Parent foundParent = userJpaRepository.findParentBySocialIdAndLoginType("SocialID", LoginType.GOOGLE).get();

        // then
        assertEquals(savedParentId, foundParent.getId());
    }

    @Test
    @DisplayName("배지 찾기 테스트")
    void findBadge() {
        // when
        Badge badge = Badge.builder()
                .type(Badge.BadgeType.ARTIST)
                .imgUrl("IMAGE URL")
                .build();

        // given
        userJpaRepository.save(badge);
        Badge foundBadge = userJpaRepository.findBadgeByType(Badge.BadgeType.ARTIST).get();

        // then
        assertEquals(foundBadge.getType(), Badge.BadgeType.ARTIST);
    }
}
