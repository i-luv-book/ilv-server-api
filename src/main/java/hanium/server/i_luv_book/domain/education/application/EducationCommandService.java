package hanium.server.i_luv_book.domain.education.application;

import hanium.server.i_luv_book.domain.education.application.dto.request.EducationContentsCreateCommand;
import hanium.server.i_luv_book.domain.education.domain.EducationContentsGenerator;
import hanium.server.i_luv_book.domain.education.domain.EducationRepository;
import hanium.server.i_luv_book.domain.fairytale.domain.Fairytale;
import hanium.server.i_luv_book.global.exception.BusinessException;
import hanium.server.i_luv_book.global.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EducationCommandService {

    private final EducationRepository educationRepository;
    private final EducationContentsGenerator educationContentsGenerator;

    // 퀴즈 생성
    public void makeQuizzes(EducationContentsCreateCommand command, Long fairytaleId) {
        Fairytale fairytale = findFairytale(fairytaleId);
        generateQuizzes(command, fairytaleId, fairytale);
    }

    private void generateQuizzes(EducationContentsCreateCommand command, Long fairytaleId, Fairytale fairytale) {
        checkQuizzesAlreadyExist(fairytale);
        educationContentsGenerator.generateQuizzes(command, fairytaleId);
    }

    private void checkQuizzesAlreadyExist(Fairytale fairytale) {
        if (fairytale.isQuizzesExistence()) {
            throw new BusinessException(ErrorCode.QUIZ_ALREADY_EXIST);
        }
    }

    private Fairytale findFairytale(Long fairytaleId) {
        return educationRepository.findFairytaleById(fairytaleId)
                .orElseThrow(() -> new BusinessException(ErrorCode.FAIRYTALE_NOT_FOUND));
    }
}
