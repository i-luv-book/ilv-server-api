package hanium.server.i_luv_book.user.domain;

import hanium.server.i_luv_book.domain.auth.domain.LoginType;
import hanium.server.i_luv_book.domain.user.application.dto.request.ChildCreateCommand;
import hanium.server.i_luv_book.domain.user.domain.Child;
import hanium.server.i_luv_book.domain.user.domain.Parent;
import hanium.server.i_luv_book.domain.user.domain.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ChildTest {

    @Test
    @DisplayName("자식계정 프로필 등록 및 조회 테스트")
    void putProfileImage() {
        Parent parent = Parent.builder()
                .socialId("소셜ID")
                .email("EMAIL")
                .loginType(LoginType.GOOGLE)
                .membershipType(Parent.MembershipType.FREE)
                .role(Role.ROLE_FREE)
                .build();
        ChildCreateCommand command = new ChildCreateCommand("자식1", LocalDate.now(), Child.Gender.MALE, parent.getId());
        Child child = Child.builder().childCreateCommand(command).parent(parent).build();

        child.putProfileImage("이미지1");
        assertEquals("이미지1", child.getProfileImageUrl());
    }
}
