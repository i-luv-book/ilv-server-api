package hanium.server.i_luv_book.domain.education.application;

import hanium.server.i_luv_book.domain.education.domain.EducationRepository;
import hanium.server.i_luv_book.global.exception.NotFoundException;
import hanium.server.i_luv_book.global.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EducationQueryService {

    private final EducationRepository educationRepository;

    // 퀴즈 유무 조회
    public boolean checkQuizExist(long fairytaleId) {
        checkFairytaleExist(fairytaleId);
        return educationRepository.checkQuizExistByFairytaleId(fairytaleId);
    }

    private void checkFairytaleExist(Long fairytaleId) {
        educationRepository.findFairytaleById(fairytaleId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.FAIRYTALE_NOT_FOUND));
    }
}
