package hanium.server.i_luv_book.domain.user.presentation;

import hanium.server.i_luv_book.domain.user.application.UserCommandService;
import hanium.server.i_luv_book.domain.user.application.UserQueryService;
import hanium.server.i_luv_book.domain.user.application.dto.response.ChildInfo;
import hanium.server.i_luv_book.domain.user.presentation.dto.UserDtoMapper;
import hanium.server.i_luv_book.domain.user.presentation.dto.request.ChildActivityDto;
import hanium.server.i_luv_book.domain.user.presentation.dto.request.ChildCreateDto;
import hanium.server.i_luv_book.domain.user.presentation.dto.request.NotificationInfoDto;
import hanium.server.i_luv_book.domain.user.presentation.dto.request.ParentUpdatePasswordDto;
import hanium.server.i_luv_book.global.security.authentication.userdetails.JwtUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author ijin
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserDtoMapper mapper;
    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;
    ;

    // 부모 APIs
    @PostMapping("/parent/child/can-add")
    public void canAddChildAccount(@AuthenticationPrincipal JwtUserDetails userDetails) {
        Long parentId = userDetails.getUserId();
        userQueryService.checkChildAdditionPossible(parentId);
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
    public List<ChildInfo> getChildren(@AuthenticationPrincipal JwtUserDetails userDetails) {
        Long parentId = userDetails.getUserId();
        return userQueryService.findChildren(parentId);
    }

    @PatchMapping("/parent/password")
    public void changePassword(@AuthenticationPrincipal JwtUserDetails userDetails, @RequestBody ParentUpdatePasswordDto dto) {
        Long parentId = userDetails.getUserId();
        userCommandService.changePassword(parentId, dto.getPassword());
    }

    @GetMapping("/parent/child")
    public ChildInfo getChild(@RequestParam(value = "nickname") String nickname) {
        return userQueryService.findChildInfo(nickname);
    }

    // 자식 APIs
    @PostMapping("/child/fairytale/time")
    public void updateFairytaleReadingDuration(@RequestBody ChildActivityDto dto) {
        userCommandService.updateFairytaleReadingDuration(mapper.toCommand(dto));
    }

    @PostMapping("/child/notification")
    public void saveChildNotificationInfo(@RequestBody NotificationInfoDto dto) {
        userCommandService.saveNotificationInfo(mapper.toCommand(dto));
    }

    @PatchMapping("/child/{nickname}/notification")
    public boolean updateNotificationAgreement(@PathVariable(value = "nickname") String nickname) {
        return userCommandService.changeNotificationAgreement(nickname);
    }

    @GetMapping("child/{nickname}/notification")
    public boolean getNotificationAgreement(@PathVariable(value = "nickname") String nickname) {
        return userQueryService.checkNotificationAgreement(nickname);
    }

    @PostMapping("/child/quiz/time")
    public void updateQuizSolvingDuration(@RequestBody ChildActivityDto dto) {
        userCommandService.updateQuizSolvingDuration(mapper.toCommand(dto));
    }
}
