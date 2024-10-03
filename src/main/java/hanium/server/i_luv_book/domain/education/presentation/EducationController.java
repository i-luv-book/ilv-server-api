package hanium.server.i_luv_book.domain.education.presentation;

import hanium.server.i_luv_book.domain.education.application.EducationCommandService;
import hanium.server.i_luv_book.domain.education.application.EducationQueryService;
import hanium.server.i_luv_book.domain.education.presentation.dto.EducationDtoMapper;
import hanium.server.i_luv_book.domain.education.presentation.dto.request.EducationContentsCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EducationController {

    private final EducationDtoMapper mapper;
    private final EducationCommandService educationCommandService;
    private final EducationQueryService educationQueryService;

    @PostMapping("/education/quizzes")
    public void makeQuizzes(@RequestParam(value = "fairytaleId") Long fairytaleId, @RequestBody EducationContentsCreateDto dto) {
        educationCommandService.makeQuizzes(mapper.toCommand(dto), fairytaleId);
    }

    @GetMapping("/education/quizzes/existence")
    public boolean checkQuizzesExistence(@RequestParam(value = "fairytaleId") Long fairytaleId) {
        return educationQueryService.checkQuizExist(fairytaleId);
    }
}
