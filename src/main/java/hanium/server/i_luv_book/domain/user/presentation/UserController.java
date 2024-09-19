package hanium.server.i_luv_book.domain.user.presentation;

import hanium.server.i_luv_book.domain.user.application.UserCommandService;
import hanium.server.i_luv_book.domain.user.presentation.dto.UserDtoMapper;
import hanium.server.i_luv_book.domain.user.presentation.dto.request.ChildCreateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ijin
 */
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserDtoMapper mapper;
    private final UserCommandService userCommandService;

    @PostMapping("/child/can-add")
    public void canAddChildAccount(@RequestParam(value = "parentId") long parentId) {
        userCommandService.checkChildAdditionPossible(parentId);
    }

    @PostMapping("/child")
    public void registerChild(@RequestParam(value = "parentId") long parentId, @RequestPart(value = "childCreateDto") @Valid ChildCreateDto childCreateDto,
                              @RequestPart(value = "image", required = false) MultipartFile image) {

        userCommandService.registerChild(mapper.toCommand(childCreateDto, parentId), image);
    }

    @DeleteMapping("/child")
    public void deleteChild(@RequestParam(value = "parentId") long parentId, @RequestParam(value = "nickname") String nickname) {
        userCommandService.deleteChild(parentId, nickname);
    }

    @PostMapping("/child/badge")
    public void registerBadge(@RequestParam(value = "childId") long childId, @RequestParam(value = "badgeId") long badgeId) {
        userCommandService.grantBadge(childId, badgeId);
    }
}
