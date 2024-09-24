package hanium.server.i_luv_book.user.application;

import hanium.server.i_luv_book.domain.auth.domain.LoginType;
import hanium.server.i_luv_book.domain.user.application.UserCommandService;
import hanium.server.i_luv_book.domain.user.application.UserQueryService;
import hanium.server.i_luv_book.domain.user.application.dto.request.ActivityInfoCreateCommand;
import hanium.server.i_luv_book.domain.user.domain.*;
import hanium.server.i_luv_book.global.exception.BusinessException;
import hanium.server.i_luv_book.domain.user.application.dto.UserCommandMapper;
import hanium.server.i_luv_book.domain.user.application.dto.request.ChildCreateCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

import static hanium.server.i_luv_book.domain.user.domain.Badge.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserCommandServiceTest {

    @Mock
    private UserCommandMapper userCommandMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private FileStore fileStore;
    @Mock
    private UserQueryService userQueryService;
    @InjectMocks
    private UserCommandService userCommandService;

    @Test
    @DisplayName("자식 계정 회원가입 테스트")
    void registerChild_Success() {
        // Given
        Parent parent = Parent.builder()
                .socialId("소셜ID")
                .email("EMAIL")
                .loginType(LoginType.GOOGLE)
                .membershipType(Parent.MembershipType.FREE)
                .role(Role.ROLE_FREE)
                .build();
        ChildCreateCommand command = new ChildCreateCommand("자식1", LocalDate.now(), Child.Gender.MALE, parent.getId());
        Child child = Child.builder().childCreateCommand(command).parent(parent).build();
        MultipartFile emptyFile = new MockMultipartFile("file", "", null, new byte[0]);

        // Mocking
        when(userQueryService.findParent(command.parentId())).thenReturn(parent);
        when(userCommandMapper.toChild(command, parent)).thenReturn(child);
        when(userRepository.save(any(Child.class))).thenReturn(1L);

        // When
        Long result = userCommandService.registerChild(command, emptyFile);

        // Then
        assertEquals(1L, result);
    }

    @Test
    @DisplayName("자식계정 추가 전 동일한 자녀 이름이 있는지 테스트")
    void registerChild_hasSameChildName() {
        // Given
        Parent parent = Parent.builder()
                .socialId("소셜ID")
                .email("EMAIL")
                .loginType(LoginType.GOOGLE)
                .membershipType(Parent.MembershipType.FREE)
                .role(Role.ROLE_FREE)
                .build();
        ChildCreateCommand childCreateCommand = new ChildCreateCommand("이복둥", LocalDate.now(), Child.Gender.MALE, 1L);
        Child existentChild = Child.builder().childCreateCommand(childCreateCommand).build();

        // When
        parent.addChild(existentChild);
        when(userQueryService.findParent(1L)).thenReturn(parent);

        // Then
        assertThrows(BusinessException.class, () -> userCommandService.registerChild(childCreateCommand, null));
    }
}
