package hanium.server.i_luv_book.user.application;

import hanium.server.i_luv_book.domain.user.application.UserQueryService;
import hanium.server.i_luv_book.domain.user.application.dto.response.ChildInfo;
import hanium.server.i_luv_book.domain.user.infra.UserQueryDao;
import hanium.server.i_luv_book.global.exception.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserQueryServiceTest {

    @Mock
    private UserQueryDao userQueryDao;

    @InjectMocks
    private UserQueryService userQueryService;

    @Test
    @DisplayName("자식 계정 정보 조회 성공")
    void findChildInfo_Success() {
        // Given
        String nickname = "닉넴1";
        ChildInfo childInfo = new ChildInfo(1L, nickname, "imageUrl");

        // Mocking
        when(userQueryDao.findChildInfosByChildNickname(nickname)).thenReturn(Optional.of(childInfo));

        // When
        ChildInfo result = userQueryService.findChildInfo(nickname);

        // Then
        assertEquals(childInfo, result);
    }

    @Test
    @DisplayName("자식 계정 정보 조회 실패 - USER_NOT_FOUND 예외 발생")
    void findChildInfo_NotFound() {
        // Given
        String nickname = "닉넴1";

        // Mocking
        when(userQueryDao.findChildInfosByChildNickname(nickname)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotFoundException.class, () -> userQueryService.findChildInfo(nickname));
    }

    @Test
    @DisplayName("자식들 계정 정보 조회 성공")
    void findChildren_Success() {
        // Given
        Long parentId = 1L;
        List<ChildInfo> children = List.of(
                new ChildInfo(1L, "아이1", "imageUrl1"),
                new ChildInfo(2L, "아이2", "imageUrl2")
        );

        // Mocking
        when(userQueryDao.findChildInfosByParentId(parentId)).thenReturn(children);

        // When
        List<ChildInfo> result = userQueryService.findChildren(parentId);

        // Then
        assertEquals(children, result);
    }
}
