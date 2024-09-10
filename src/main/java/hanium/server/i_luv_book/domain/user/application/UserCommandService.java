package hanium.server.i_luv_book.domain.user.application;

import hanium.server.i_luv_book.domain.user.application.dto.UserCommandMapper;
import hanium.server.i_luv_book.domain.user.application.dto.request.ChildCreateCommand;
import hanium.server.i_luv_book.domain.user.domain.*;
import hanium.server.i_luv_book.global.exception.BusinessException;
import hanium.server.i_luv_book.global.exception.NotFoundException;
import hanium.server.i_luv_book.global.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ijin
 */
@Service
@RequiredArgsConstructor
public class UserCommandService {

    private final UserCommandMapper userCommandMapper;
    private final UserRepository userRepository;
    private final FileStore fileStore;

    // 자식 계정 가입
    @Transactional
    public Long registerChild(ChildCreateCommand command, MultipartFile image) {
        Parent parent = findParent(command.parentId());
        checkIfChildNameAlreadyExists(command, parent);
        Child child = createChild(command, parent, image);

        return saveChild(parent, child);
    }

    // 자식 추가 여부 체크
    @Transactional(readOnly = true)
    public void checkChildAdditionPossible(Long parentId) {
        Parent parent = findParent(parentId);
        int currentNumberOfChildren = getCurrentNumberOfChildren(parentId);
        checkChildAdditionPossible(parent, currentNumberOfChildren);
    }

    // 자식 삭제
    @Transactional
    public void deleteChild(Long parentId, String nickname) {
        userRepository.deleteChild(parentId, nickname);
    }

    // 배지 획득
    @Transactional
    public Long grantBadge(Long childId, Long badgeId) {
        Child child = findChild(childId);
        Badge badge = findBadge(badgeId);
        ChildBadge childBadge = createChildBadge(child, badge);
        return saveChildBadge(child, childBadge, badge);
    }

    // 비밀번호 변경
    @Transactional
    public void changePassword(Long parentId, String password) {
        Parent parent = findParent(parentId);
        parent.updatePassword(password);
    }

    private Long saveChildBadge(Child child, ChildBadge childBadge, Badge badge) {
        child.addChildBadge(childBadge);
        badge.addChildBadge(childBadge);
        return userRepository.save(childBadge);
    }

    private ChildBadge createChildBadge(Child child, Badge badge) {
        return userCommandMapper.toChildBadge(child, badge);
    }

    private Child findChild(Long childId) {
        return userRepository.findChildById(childId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }

    private Badge findBadge(Long badgeId) {
        return userRepository.findBadgeById(badgeId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BADGE_NOT_FOUND));
    }

    private void checkIfChildNameAlreadyExists(ChildCreateCommand command, Parent parent) {
        if (parent.hasChildWithName(command.nickname())) {
            throw new BusinessException(ErrorCode.USER_ALREADY_EXISTED);
        }
    }

    private Child createChild(ChildCreateCommand command, Parent parent, MultipartFile image) {
        Child child = userCommandMapper.toChild(command, parent);
        uploadProfileImage(image, child);
        return child;
    }

    private void uploadProfileImage(MultipartFile image, Child child) {
        if (!image.isEmpty()) {
            String imageUrl = fileStore.upload(image);
            child.putProfileImage(imageUrl);
        }
    }

    private void checkChildAdditionPossible(Parent parent, int currentNumberOfChildren) {
        if (!parent.canAddChild(currentNumberOfChildren)) {
            throw new BusinessException(ErrorCode.LIMITED_ACCESS);
        }
    }

    private Long saveChild(Parent parent, Child child) {
        parent.addChild(child);
        return userRepository.save(child);
    }

    private int getCurrentNumberOfChildren(Long parentId) {
        return userRepository.countChildrenByParentId(parentId);
    }

    private Parent findParent(long parentId) {
        return userRepository.findParentById(parentId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND, parentId));
    }
}
