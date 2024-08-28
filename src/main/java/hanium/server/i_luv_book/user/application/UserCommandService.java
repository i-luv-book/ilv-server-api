package hanium.server.i_luv_book.user.application;

import hanium.server.i_luv_book.exception.BusinessException;
import hanium.server.i_luv_book.exception.NotFoundException;
import hanium.server.i_luv_book.exception.code.ErrorCode;
import hanium.server.i_luv_book.user.application.dto.UserCommandMapper;
import hanium.server.i_luv_book.user.application.dto.request.ChildCreateCommand;
import hanium.server.i_luv_book.user.application.dto.request.ParentCreateCommand;
import hanium.server.i_luv_book.user.domain.Child;
import hanium.server.i_luv_book.user.domain.Parent;
import hanium.server.i_luv_book.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ijin
 */
@Service
@RequiredArgsConstructor
public class UserCommandService {

    private final UserCommandMapper userCommandMapper;
    private final UserRepository userRepository;

    // 부모 계정 가입
    @Transactional
    public Long registerParent(ParentCreateCommand command) {
        Parent parent = createParent(command);
        return saveParent(parent);
    }

    // 자식 계정 가입
    @Transactional
    public Long registerChild(ChildCreateCommand command) {
        Parent parent = findParent(command.parentId());
        Child child = createChild(command, parent);
        return saveChild(parent, child);
    }

    // 자식 추가 여부 체크
    @Transactional(readOnly = true)
    public void checkChildAdditionPossible(Long parentId) {
        Parent parent = findParent(parentId);
        int currentNumberOfChildren = getCurrentNumberOfChildren(parentId);
        checkChildAdditionPossible(parent, currentNumberOfChildren);
    }

    private Long saveParent(Parent parent) {
        return userRepository.save(parent);
    }

    private Parent createParent(ParentCreateCommand command) {
        return userCommandMapper.toParent(command);
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

    private Child createChild(ChildCreateCommand command, Parent parent) {
        return userCommandMapper.toChild(command, parent);
    }

    private int getCurrentNumberOfChildren(Long parentId) {
        return userRepository.countChildrenByParentId(parentId);
    }

    private Parent findParent(long parentId) {
        return userRepository.findParentById(parentId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND, parentId));
    }
}
