package hanium.server.i_luv_book.domain.user.presentation;

import hanium.server.i_luv_book.domain.user.application.UserCommandService;
import hanium.server.i_luv_book.domain.user.presentation.dto.UserDtoMapper;
import hanium.server.i_luv_book.domain.user.presentation.dto.request.ChildCreateDto;
import hanium.server.i_luv_book.global.security.authentication.userdetails.JwtUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ijin
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserDtoMapper mapper;
    private final UserCommandService userCommandService;

    // 부모 APIs
    @PostMapping("/parent/child/can-add")
    public void canAddChildAccount(@AuthenticationPrincipal JwtUserDetails userDetails) {
        Long parentId = userDetails.getUserId();
        userCommandService.checkChildAdditionPossible(parentId);
    }

    @PostMapping("/parent/child")
    public void registerChild(@AuthenticationPrincipal JwtUserDetails userDetails,
                              @RequestPart(value = "childCreateDto") @Valid ChildCreateDto childCreateDto, @RequestPart(value = "image", required = false) MultipartFile image) {
        Long parentId = userDetails.getUserId();
        userCommandService.registerChild(mapper.toCommand(childCreateDto, parentId), image);
    }

    @DeleteMapping("/parent/child")
    public void deleteChild(@AuthenticationPrincipal JwtUserDetails userDetails, @RequestParam(value = "nickname") String nickname) {
        Long parentId = userDetails.getUserId();
        userCommandService.deleteChild(parentId, nickname);
    }

    @GetMapping("/parent/children")
    public void getChildren(@AuthenticationPrincipal JwtUserDetails userDetails) {
        Long parentId = userDetails.getUserId();
    }

    // 자식 APIs
    @PostMapping("/child/badge")
    public void registerBadge(@AuthenticationPrincipal JwtUserDetails userDetails, @RequestParam(value = "badgeId") long badgeId) {
        Long childId = userDetails.getUserId();
        userCommandService.grantBadge(childId, badgeId);
    }
}
