package hanium.server.i_luv_book.domain.user.application;

import hanium.server.i_luv_book.domain.user.application.dto.response.ChildInfo;
import hanium.server.i_luv_book.domain.user.infra.UserQueryDao;
import hanium.server.i_luv_book.global.exception.NotFoundException;
import hanium.server.i_luv_book.global.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ijin
 */
@Service
@RequiredArgsConstructor
public class UserQueryService {

    private final UserQueryDao userQueryDao;

    // 자식계정 정보 조회
    @Transactional(readOnly = true)
    public ChildInfo findChildInfo(String nickname) {
        return findChildInfoByChildNickname(nickname);
    }

    // 자식들 계정 조회
    @Transactional(readOnly = true)
    public List<ChildInfo> findChildren(Long parentId) {
        return findChildrenInfosByParentId(parentId);
    }

    private List<ChildInfo> findChildrenInfosByParentId(Long parentId) {
        return userQueryDao.findChildInfosByParentId(parentId);
    }

    private ChildInfo findChildInfoByChildNickname(String nickname) {
        return userQueryDao.findChildInfosByChildNickname(nickname)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }
}
