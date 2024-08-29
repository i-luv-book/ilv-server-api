package hanium.server.i_luv_book.user.infra;

import hanium.server.i_luv_book.domain.user.application.dto.request.ChildCreateCommand;
import hanium.server.i_luv_book.domain.user.application.dto.request.ParentCreateCommand;
import hanium.server.i_luv_book.domain.user.domain.Child;
import hanium.server.i_luv_book.domain.user.domain.Parent;
import hanium.server.i_luv_book.domain.user.domain.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserJpaRepositoryTest {

    @Autowired
    private UserRepository userJpaRepository;

    @Test
    @DisplayName("부모 저장 및 조회 테스트")
    @Transactional
    void saveParent() {
        // given
        ParentCreateCommand parentCreateCommand = new ParentCreateCommand("부모1", "비밀번호1");
        Parent parent = Parent.builder().parentCreateCommand(parentCreateCommand).build();

        // when
        Long savedParentId = userJpaRepository.save(parent);
        Parent savedParent = userJpaRepository.findParentById(savedParentId).get();

        // Then
        assertEquals(parent.getEmail(), savedParent.getEmail());
    }

    @Test
    @DisplayName("자식 저장 및 부모의 자식 조회 테스트")
    @Transactional
    void saveChildAndCount() {
        // given
        ParentCreateCommand parentCreateCommand = new ParentCreateCommand("부모1", "비밀번호1");
        Parent parent = Parent.builder().parentCreateCommand(parentCreateCommand).build();
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
}
