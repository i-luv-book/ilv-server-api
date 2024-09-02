package hanium.server.i_luv_book.user.application;

import hanium.server.i_luv_book.domain.user.application.UserCommandService;
import hanium.server.i_luv_book.domain.user.domain.*;
import hanium.server.i_luv_book.domain.user.presentation.dto.response.TokenDto;
import hanium.server.i_luv_book.global.exception.BusinessException;
import hanium.server.i_luv_book.global.exception.NotFoundException;
import hanium.server.i_luv_book.domain.user.application.dto.UserCommandMapper;
import hanium.server.i_luv_book.domain.user.application.dto.request.ChildCreateCommand;
import hanium.server.i_luv_book.domain.user.application.dto.request.ParentCreateCommand;
import hanium.server.i_luv_book.global.jwt.utils.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserCommandServiceTest {

    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private UserCommandMapper userCommandMapper;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserCommandService userCommandService;

    @Test
    @DisplayName("부모 계정 회원가입 테스트")
    void registerParent_Success() {
        // Given
        ParentCreateCommand command = new ParentCreateCommand("email1", "password1");
        Parent parent = Parent.builder().parentCreateCommand(command).build();

        when(userCommandMapper.toParent(command)).thenReturn(parent);
        when(userRepository.save(parent)).thenReturn(1L);
        when(jwtUtil.generateAccessToken(1L, Role.ROLE_FREE)).thenReturn("AccessToken");
        when(jwtUtil.generateRefreshToken(1L)).thenReturn("RefreshToken");

        // When
        TokenDto savedTokenDto = new TokenDto("AccessToken", "RefreshToken");
        TokenDto testTokenDto = userCommandService.registerParent(command);

        // Then
        assertEquals(savedTokenDto, testTokenDto);
    }

    @Test
    @DisplayName("부모 계정 찾지 못했을 때 발생하는 예외테스트")
    void parent_NotFound() {
        // Given
        long nonExistentParentId = 999L;

        // Mocking
        when(userRepository.findParentById(nonExistentParentId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundException.class, () -> userCommandService.checkChildAdditionPossible(nonExistentParentId));

        verify(userRepository, times(1)).findParentById(nonExistentParentId);
        verify(userRepository, never()).countChildrenByParentId(anyLong());
        verify(userRepository, never()).save(any(Child.class));
    }

    @Test
    @DisplayName("자식 계정 회원가입 테스트")
    void registerChild_Success() {
        // Given
        Parent parent = Parent.builder().parentCreateCommand(new ParentCreateCommand("부모1", "비밀번호1")).build();
        ChildCreateCommand command = new ChildCreateCommand("자식1", LocalDate.now(), Child.Gender.MALE, parent.getId());
        Child child = Child.builder().childCreateCommand(command).parent(parent).build();
        MultipartFile emptyFile = new MockMultipartFile("file", "", null, new byte[0]);

        // Mocking
        when(userRepository.findParentById(command.parentId())).thenReturn(Optional.of(parent));
        when(userCommandMapper.toChild(command, parent)).thenReturn(child);
        when(userRepository.save(any(Child.class))).thenReturn(1L);

        // When
        Long result = userCommandService.registerChild(command, emptyFile);

        // Then
        assertEquals(1L, result);
    }

    @Test
    @DisplayName("자식계정 추가 전 멤버쉽 제한 테스트")
    void registerChild_LimitedMembership() {
        // Given
        Parent parent = Parent.builder()
                .parentCreateCommand(new ParentCreateCommand("부모1", "비밀번호1"))
                .build();

        // Mocking
        when(userRepository.findParentById(parent.getId())).thenReturn(Optional.of(parent));
        when(userRepository.countChildrenByParentId(parent.getId())).thenReturn(1); // 자식 제한 초과

        // When & Then
        assertThrows(BusinessException.class, () -> userCommandService.checkChildAdditionPossible(parent.getId()));
    }

    @Test
    @DisplayName("자식계정 추가 전 동일한 자녀 이름이 있는지 테스트")
    void registerChild_hasSameChildName() {
        // Given
        ParentCreateCommand parentCreateCommand = new ParentCreateCommand("부모1", "비밀번호1");
        Parent parent = Parent.builder().parentCreateCommand(parentCreateCommand).build();
        ChildCreateCommand childCreateCommand = new ChildCreateCommand("이복둥", LocalDate.now(), Child.Gender.MALE, 1L);
        Child existentChild = Child.builder().childCreateCommand(childCreateCommand).build();

        // When
        parent.addChild(existentChild);
        when(userRepository.findParentById(1L)).thenReturn(Optional.of(parent));

        // Then
        assertThrows(BusinessException.class, () -> userCommandService.registerChild(childCreateCommand, null));
    }

    @Test
    @DisplayName("배지 부여 성공 테스트")
    void grantBadge_Success() {
        // Given
        Long childId = 1L;
        Long badgeId = 1L;
        Child child = mock(Child.class);
        Badge badge = mock(Badge.class);
        ChildBadge childBadge = mock(ChildBadge.class);

        when(userRepository.findChildById(childId)).thenReturn(Optional.of(child));
        when(userRepository.findBadgeById(badgeId)).thenReturn(Optional.of(badge));
        when(userCommandMapper.toChildBadge(child, badge)).thenReturn(childBadge);
        when(userRepository.save(childBadge)).thenReturn(1L);

        // When
        Long result = userCommandService.grantBadge(childId, badgeId);

        // Then
        assertEquals(1L, result);
        verify(userRepository, times(1)).findChildById(childId);
        verify(userRepository, times(1)).findBadgeById(badgeId);
        verify(userCommandMapper, times(1)).toChildBadge(child, badge);
        verify(userRepository, times(1)).save(childBadge);
    }
}
