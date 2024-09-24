package hanium.server.i_luv_book.domain.user.application;

import hanium.server.i_luv_book.domain.user.application.dto.response.ChildInfo;
import hanium.server.i_luv_book.domain.user.domain.Badge;
import hanium.server.i_luv_book.domain.user.domain.Child;
import hanium.server.i_luv_book.domain.user.domain.Parent;
import hanium.server.i_luv_book.domain.user.domain.UserRepository;
import hanium.server.i_luv_book.domain.user.domain.notification.NotificationInfo;
import hanium.server.i_luv_book.domain.user.infra.UserQueryDao;
import hanium.server.i_luv_book.global.exception.BusinessException;
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
@Transactional(readOnly = true)
public class UserQueryService {

    private final UserRepository userRepository;
    private final UserQueryDao userQueryDao;

    // 자식계정 정보 조회
    public ChildInfo findChildInfo(String nickname) {
        return findChildInfoByChildNickname(nickname);
    }

    // 자식들 계정 조회
    public List<ChildInfo> findChildren(Long parentId) {
        return findChildrenInfosByParentId(parentId);
    }

    // 자식 추가 여부 체크
    public void checkChildAdditionPossible(Long parentId) {
        Parent parent = findParent(parentId);
        int currentNumberOfChildren = getCurrentNumberOfChildren(parentId);
        checkChildAdditionPossible(parent, currentNumberOfChildren);
    }

    // 알림 유무 조회
    public boolean checkNotificationAgreement(String nickname) {
        return userRepository.checkNotificationAgreement(nickname);
    }

    // 부모 계정 찾기
    public Parent findParent(long parentId) {
        return userRepository.findParentById(parentId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND, parentId));
    }

    // 자식 계정 찾기
    public Child findChild(long parentId, String nickname) {
        return userRepository.findChildByParentIdAndNickname(parentId, nickname)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    // 배지 찾기
    public Badge findBadge(Badge.BadgeType badgeType) {
        return userRepository.findBadgeByType(badgeType)
                .orElseThrow(() -> new BusinessException(ErrorCode.VIOLATED_DATA_INTEGRITY));
    }

    // 알림 정보 조회
    public NotificationInfo findNotificationInfo(long childId) {
        return userRepository.findNotificationInfoByChildId(childId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.FCM_TOKEN_NOT_FOUND));
    }

    private List<ChildInfo> findChildrenInfosByParentId(Long parentId) {
        return userQueryDao.findChildInfosByParentId(parentId);
    }

    private ChildInfo findChildInfoByChildNickname(String nickname) {
        return userQueryDao.findChildInfosByChildNickname(nickname)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    private void checkChildAdditionPossible(Parent parent, int currentNumberOfChildren) {
        if (!parent.canAddChild(currentNumberOfChildren)) {
            throw new BusinessException(ErrorCode.LIMITED_ACCESS);
        }
    }

    private int getCurrentNumberOfChildren(Long parentId) {
        return userRepository.countChildrenByParentId(parentId);
    }
}
