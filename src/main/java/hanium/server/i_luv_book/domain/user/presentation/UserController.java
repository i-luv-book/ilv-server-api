package hanium.server.i_luv_book.domain.user.presentation;

import hanium.server.i_luv_book.domain.user.application.UserCommandService;
import hanium.server.i_luv_book.domain.user.presentation.dto.UserDtoMapper;
import hanium.server.i_luv_book.domain.user.presentation.dto.request.ChildCreateDto;
import hanium.server.i_luv_book.domain.user.presentation.dto.request.ParentCreateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        userCommandService.checkChildAdditionPossible(parentId);
    }

    @PostMapping("/api/v1/user/child")
    public void registerChild(@RequestParam(value = "parentId") long parentId, @RequestPart(value = "childCreateDto") @Valid ChildCreateDto childCreateDto,
                              @RequestPart(value = "image", required = false) MultipartFile image) {
        userCommandService.registerChild(mapper.toCommand(childCreateDto, parentId), image);
    }

    @PostMapping("/api/v1/user/parent")
    public void registerParent(@RequestBody @Valid ParentCreateDto parentCreateDto) {
        userCommandService.registerParent(mapper.toCommand(parentCreateDto));
    }
}
