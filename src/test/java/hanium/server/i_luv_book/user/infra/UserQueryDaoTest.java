package hanium.server.i_luv_book.user.infra;

import hanium.server.i_luv_book.domain.auth.domain.LoginType;
import hanium.server.i_luv_book.domain.user.application.dto.request.ChildCreateCommand;
import hanium.server.i_luv_book.domain.user.application.dto.response.ChildInfo;
import hanium.server.i_luv_book.domain.user.domain.Child;
import hanium.server.i_luv_book.domain.user.domain.Parent;
import hanium.server.i_luv_book.domain.user.domain.Role;
import hanium.server.i_luv_book.domain.user.domain.UserRepository;
import hanium.server.i_luv_book.domain.user.infra.UserQueryDao;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * @author ijin
 */
@SpringBootTest
class UserQueryDaoTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserQueryDao userQueryDao;
    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("부모 ID로 자식계정 리스트 조회 테스트")
    @Transactional
    void getChildInfosByParentId() {
        // given
        Parent parent = Parent.builder()
                .socialId("소셜ID")
                .email("EMAIL")
                .loginType(LoginType.GOOGLE)
                .membershipType(Parent.MembershipType.FREE)
                .role(Role.ROLE_PAID)
                .build();
        Long savedParentId = userRepository.save(parent);

        ChildCreateCommand childCreateCommand1 = new ChildCreateCommand("자식1", LocalDate.now(), Child.Gender.MALE,  savedParentId);
        Child child1 = Child.builder().childCreateCommand(childCreateCommand1).build();
        child1.putProfileImage("프로필이미지1");
        ChildCreateCommand childCreateCommand2 = new ChildCreateCommand("자식2", LocalDate.now(), Child.Gender.MALE, savedParentId);
        Child child2 = Child.builder().childCreateCommand(childCreateCommand2).build();
        child2.putProfileImage("프로필이미지1");
        ChildCreateCommand childCreateCommand3 = new ChildCreateCommand("자식3", LocalDate.now(), Child.Gender.FEMALE, savedParentId);
        Child child3 = Child.builder().childCreateCommand(childCreateCommand3).build();

        // when
        parent.addChild(child1);
        parent.addChild(child2);
        parent.addChild(child3);
        child1.registerParent(parent);
        child2.registerParent(parent);
        child3.registerParent(parent);

        em.persist(child1);
        em.persist(child2);
        em.persist(child3);

        // then
        List<ChildInfo> childInfos = userQueryDao.findChildInfosByParentId(savedParentId);
        Assertions.assertEquals(3, childInfos.size());
    }

    @Test
    @DisplayName("자식 닉네임으로 정보 조회 테스트")
    @Transactional
    void getChildInfoByChildNickname() {
        // given
        Parent parent = Parent.builder()
                .socialId("소셜ID")
                .email("EMAIL")
                .loginType(LoginType.GOOGLE)
                .membershipType(Parent.MembershipType.FREE)
                .role(Role.ROLE_PAID)
                .build();
        Long savedParentId = userRepository.save(parent);
        ChildCreateCommand childCreateCommand1 = new ChildCreateCommand("자식1", LocalDate.now(), Child.Gender.MALE,  savedParentId);
        Child child1 = Child.builder().childCreateCommand(childCreateCommand1).build();
        child1.putProfileImage("프로필이미지1");

        // when
        Long savedChildId = userRepository.save(child1);

        // then
        ChildInfo childInfo = userQueryDao.findChildInfosByChildNickname("자식1").get();
        Assertions.assertEquals(childInfo.nickname(), "자식1");
    }
}
