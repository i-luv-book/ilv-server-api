package hanium.server.i_luv_book.domain.user.application;

import hanium.server.i_luv_book.domain.user.application.dto.UserCommandMapper;
import hanium.server.i_luv_book.domain.user.application.dto.request.ChildCreateCommand;
import hanium.server.i_luv_book.domain.user.application.dto.request.ParentCreateCommand;
import hanium.server.i_luv_book.domain.user.domain.*;
import hanium.server.i_luv_book.domain.user.presentation.dto.response.TokenDto;
import hanium.server.i_luv_book.global.exception.BusinessException;
import hanium.server.i_luv_book.global.exception.NotFoundException;
import hanium.server.i_luv_book.global.exception.code.ErrorCode;
import hanium.server.i_luv_book.global.jwt.util.JwtUtil;
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

    private final JwtUtil jwtUtil;
    private final UserCommandMapper userCommandMapper;
    private final UserRepository userRepository;
    private final FileStore fileStore;

    // 부모 계정 가입
    @Transactional
    public TokenDto registerParent(ParentCreateCommand command) {
        Parent parent = createParent(command);
        Long parentId = saveParent(parent);
        return generateTokens(parentId);
    }

    // 자식 계정 가입
    @Transactional
    public Long registerChild(ChildCreateCommand command, MultipartFile image) {
        Parent parent = findParent(command.parentId());
        Child child = createChild(command, parent);
        uploadProfileImage(image, child);
        return saveChild(parent, child);
    }

    // 자식 추가 여부 체크
    @Transactional(readOnly = true)
    public void checkChildAdditionPossible(Long parentId) {
        Parent parent = findParent(parentId);
        int currentNumberOfChildren = getCurrentNumberOfChildren(parentId);
        checkChildAdditionPossible(parent, currentNumberOfChildren);
    }

    private TokenDto generateTokens(Long parentId) {
        String accessToken = jwtUtil.generateAccessToken(parentId, Role.ROLE_FREE);
        String refreshToken = jwtUtil.generateRefreshToken(parentId);
        return new TokenDto(accessToken, refreshToken);
    }

    private void uploadProfileImage(MultipartFile image, Child child) {
        if (!image.isEmpty()) {
            String imageUrl = fileStore.upload(image);
            child.putProfileImage(imageUrl);
        }
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
