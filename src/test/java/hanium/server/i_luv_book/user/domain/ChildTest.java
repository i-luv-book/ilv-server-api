package hanium.server.i_luv_book.user.domain;

import hanium.server.i_luv_book.user.application.dto.request.ChildCreateCommand;
import hanium.server.i_luv_book.user.application.dto.request.ParentCreateCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ChildTest {

    @Test
    @DisplayName("자식계정 프로필 등록 및 조회 테스트")
    void putProfileImage() {
        Parent parent = Parent.builder().parentCreateCommand(new ParentCreateCommand("부모1", "비밀번호1")).build();
        ChildCreateCommand command = new ChildCreateCommand("자식1", LocalDate.now(), Child.Gender.MALE, parent.getId());
        Child child = Child.builder().childCreateCommand(command).parent(parent).build();

        child.putProfileImage("이미지1");
        assertEquals("이미지1", child.getProfileImageUrl());
    }
}
