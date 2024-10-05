package hanium.server.i_luv_book.domain.education.presentation;

import hanium.server.i_luv_book.domain.education.application.EducationCommandService;
import hanium.server.i_luv_book.domain.education.application.EducationQueryService;
import hanium.server.i_luv_book.domain.education.application.dto.response.SolvedQuizzesInfo;
import hanium.server.i_luv_book.domain.education.presentation.dto.EducationDtoMapper;
import hanium.server.i_luv_book.domain.education.presentation.dto.request.EducationContentsCreateDto;
import hanium.server.i_luv_book.global.security.authentication.userdetails.JwtUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EducationController {

    private final EducationDtoMapper mapper;
    private final EducationCommandService educationCommandService;
    private final EducationQueryService educationQueryService;

    // 퀴즈 생성
    @PostMapping("/education/quizzes")
    public void makeQuizzes(@RequestParam(value = "fairytaleId") Long fairytaleId, @RequestBody EducationContentsCreateDto dto) {
        educationCommandService.makeQuizzes(mapper.toCommand(dto), fairytaleId);
    }

    // 퀴즈 유무 확인
    @GetMapping("/education/quizzes/existence")
    public boolean checkQuizzesExistence(@RequestParam(value = "fairytaleId") Long fairytaleId) {
        return educationQueryService.checkQuizExist(fairytaleId);
    }

    // 퀴즈 통계 조회
    @GetMapping("/education/quizzes/statistics")
    public SolvedQuizzesInfo getSolvedQuizzesStatistics(@AuthenticationPrincipal JwtUserDetails userDetails, @RequestParam(value = "nickname") String nickname) {
        Long parentId = userDetails.getUserId();
        return educationQueryService.getSolvedQuizzes(nickname, parentId);
    }
}
