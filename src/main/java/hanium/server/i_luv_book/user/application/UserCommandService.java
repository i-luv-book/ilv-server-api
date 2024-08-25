package hanium.server.i_luv_book.user.application;

import hanium.server.i_luv_book.exception.BusinessException;
import hanium.server.i_luv_book.exception.NotFoundException;
import hanium.server.i_luv_book.exception.code.ErrorCode;
import hanium.server.i_luv_book.user.application.dto.UserCommandMapper;
import hanium.server.i_luv_book.user.application.dto.request.ChildCreateCommand;
import hanium.server.i_luv_book.user.domain.Child;
import hanium.server.i_luv_book.user.domain.Parent;
import hanium.server.i_luv_book.user.domain.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author ijin
 */
@Service
@RequiredArgsConstructor
public class UserCommandService {

    private final UserCommandMapper userCommandMapper;
    private final UserRepository userRepository;

    @Transactional
    public void addChild(ChildCreateCommand command) {
        Parent parent = findParent(command.parentId());
        int currentNumberOfChildren = getCurrentNumberOfChildren(command);

        validateChildAddition(parent, currentNumberOfChildren);

        Child child = createChild(command, parent);
        saveChild(parent, child);
    }

    private void validateChildAddition(Parent parent, int currentNumberOfChildren) {
        if (!parent.canAddChild(currentNumberOfChildren)) {
            throw new BusinessException(ErrorCode.LIMITED_ACCESS);
        }
    }

    private void saveChild(Parent parent, Child child) {
        parent.addChild(child);
        userRepository.save(child);
    }

    private Child createChild(ChildCreateCommand command, Parent parent) {
        return userCommandMapper.toChild(command, parent);
    }

    private int getCurrentNumberOfChildren(ChildCreateCommand command) {
        return userRepository.countChildrenByParentId(command.parentId());
    }

    private Parent findParent(long parentId) {
        return userRepository.findParentById(parentId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND, parentId));
    }
}
