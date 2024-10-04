package hanium.server.i_luv_book.domain.education.application;

import hanium.server.i_luv_book.domain.education.application.dto.request.EducationContentsCreateCommand;
import hanium.server.i_luv_book.domain.education.domain.EducationContentsGenerator;
import hanium.server.i_luv_book.global.exception.BusinessException;
import hanium.server.i_luv_book.global.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EducationCommandService {

    private final EducationContentsGenerator educationContentsGenerator;
    private final EducationQueryService educationQueryService;

    // 퀴즈 생성
    public void makeQuizzes(EducationContentsCreateCommand command, Long fairytaleId) {
        generateQuizzes(command, fairytaleId);
    }

    private void generateQuizzes(EducationContentsCreateCommand command, Long fairytaleId) {
        if (!checkQuizzesExist(fairytaleId)) {
            educationContentsGenerator.generateQuizzes(command, fairytaleId);
        } else {
            throw new BusinessException(ErrorCode.QUIZ_ALREADY_EXIST);
        }
    }

    private boolean checkQuizzesExist(Long fairytaleId) {
        return educationQueryService.checkQuizExist(fairytaleId);
    }
}
