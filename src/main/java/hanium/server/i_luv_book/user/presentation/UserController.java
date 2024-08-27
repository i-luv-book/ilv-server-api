package hanium.server.i_luv_book.user.presentation;

import hanium.server.i_luv_book.user.application.UserCommandService;
import hanium.server.i_luv_book.user.presentation.dto.UserDtoMapper;
import hanium.server.i_luv_book.user.presentation.dto.request.ChildCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ijin
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserDtoMapper mapper;
    private final UserCommandService userCommandService;

    @PostMapping("/api/v1/user/child/can-add")
    public void canAddChildAccount(@RequestParam(value = "parentId") long parentId) {
        userCommandService.canAddChildAccount(parentId);
    }

    @PostMapping("/api/v1/user/child")
    public void canAddChildAccount(@RequestBody ChildCreateDto childCreateDto, @RequestParam(value = "parentId") long parentId) {
        userCommandService.registerChild(mapper.toCommand(childCreateDto, parentId));
    }
}
