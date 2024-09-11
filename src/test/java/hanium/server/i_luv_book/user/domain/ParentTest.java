package hanium.server.i_luv_book.user.domain;


import hanium.server.i_luv_book.domain.auth.domain.LoginType;
import hanium.server.i_luv_book.domain.user.application.dto.request.ChildCreateCommand;
import hanium.server.i_luv_book.domain.user.domain.Child;
import hanium.server.i_luv_book.domain.user.domain.Parent;
import hanium.server.i_luv_book.domain.user.domain.Role;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ParentTest {

    @Test
    void updateMembershipType() {
        // given
        Parent parent = Parent.builder()
                .socialId("소셜ID")
                .email("EMAIL")
                .loginType(LoginType.GOOGLE)
                .membershipType(Parent.MembershipType.FREE)
                .role(Role.ROLE_FREE)
                .build();

        // when&then
        parent.updateMembershipType(Parent.MembershipType.PAID_BASIC);
        assertEquals(parent.getMembershipType(), Parent.MembershipType.PAID_BASIC);
    }

    @Test
    void updatePasswordTest() {
        // given
        Parent parent = Parent.builder()
                .socialId("소셜ID")
                .email("EMAIL")
                .loginType(LoginType.GOOGLE)
                .membershipType(Parent.MembershipType.FREE)
                .role(Role.ROLE_FREE)
                .build();

        // when&then
        parent.updatePassword("비밀번호");
        assertEquals("비밀번호", parent.getPassword());
    }

    @Test
    void canAddChild() {
        // given
        Parent parent = Parent.builder()
                .socialId("소셜ID")
                .email("EMAIL")
                .loginType(LoginType.GOOGLE)
                .membershipType(Parent.MembershipType.FREE)
                .role(Role.ROLE_FREE)
                .build();

        // when
        boolean result = parent.canAddChild(1);

        // then
        assertFalse(result);
    }

    @Test
    void hasChildWithName() {
        // given
        Parent parent = Parent.builder()
                .socialId("소셜ID")
                .email("EMAIL")
                .loginType(LoginType.GOOGLE)
                .membershipType(Parent.MembershipType.FREE)
                .role(Role.ROLE_FREE)
                .build();
        ChildCreateCommand childCreateCommand = new ChildCreateCommand("이복둥", LocalDate.now(), Child.Gender.MALE, 1L);
        Child child = Child.builder().childCreateCommand(childCreateCommand).build();

        // when
        parent.addChild(child);

        // then
        boolean result = parent.hasChildWithName(child.getNickname());
        assertTrue(result);
    }
}
