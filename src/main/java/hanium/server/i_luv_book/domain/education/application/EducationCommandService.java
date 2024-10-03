package hanium.server.i_luv_book.domain.education.application;

import hanium.server.i_luv_book.domain.education.application.dto.request.EducationContentsCreateCommand;
import hanium.server.i_luv_book.domain.education.domain.EducationContentsGenerator;
import hanium.server.i_luv_book.domain.education.domain.EducationRepository;
import hanium.server.i_luv_book.global.exception.NotFoundException;
import hanium.server.i_luv_book.global.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EducationCommandService {

    private final EducationContentsGenerator educationContentsGenerator;
    private final EducationRepository educationRepository;

    // 퀴즈 생성
    public void makeQuizzes(EducationContentsCreateCommand command, Long fairytaleId) {
        checkFairytaleExist(fairytaleId);
        educationContentsGenerator.generateQuizzes(command, fairytaleId);
    }

    private void checkFairytaleExist(Long fairytaleId) {
        educationRepository.findFairytaleById(fairytaleId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.FAIRYTALE_NOT_FOUND));
    }
}
