package hanium.server.i_luv_book.domain.education.application;

import hanium.server.i_luv_book.domain.education.application.dto.response.FairytaleQuizzesInfo;
import hanium.server.i_luv_book.domain.education.application.dto.response.SolvedQuizzesCountsInfo;
import hanium.server.i_luv_book.domain.education.application.dto.response.SolvedQuizzesInfo;
import hanium.server.i_luv_book.domain.education.application.dto.response.SolvedQuizzesTypesInfo;
import hanium.server.i_luv_book.domain.education.domain.EducationRepository;
import hanium.server.i_luv_book.domain.education.infra.EducationQueryDao;
import hanium.server.i_luv_book.domain.user.application.UserQueryService;
import hanium.server.i_luv_book.domain.user.domain.Child;
import hanium.server.i_luv_book.global.exception.NotFoundException;
import hanium.server.i_luv_book.global.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EducationQueryService {

    private final EducationQueryDao educationQueryDao;
    private final UserQueryService userQueryService;
    private final EducationRepository educationRepository;

    // 퀴즈 유무 조회
    public boolean checkQuizExist(long fairytaleId) {
        checkFairytaleExist(fairytaleId);
        return educationRepository.checkQuizExistByFairytaleId(fairytaleId);
    }

    // 푼 퀴즈 통계치 조회
    public SolvedQuizzesInfo getSolvedQuizzes(String nickname, Long parentId) {
        Child child = findChild(nickname, parentId);
        return countSolvedQuizzesStatistics(child);
    }

    // 푼 퀴즈 정보 조회
    public List<FairytaleQuizzesInfo> getFairytaleQuizzes(String nickname, Long parentId, Long fairytaleId) {
        Child child = findChild(nickname, parentId);
        return findFairytaleQuizzesInfo(fairytaleId, child);
    }

    private List<FairytaleQuizzesInfo> findFairytaleQuizzesInfo(Long fairytaleId, Child child) {
        return educationQueryDao.findFairytaleQuizzesInfo(fairytaleId, child.getId());
    }

    private SolvedQuizzesInfo countSolvedQuizzesStatistics(Child child) {
        return new SolvedQuizzesInfo(countSolvedQuizzesTypesStatistics(child), countSolvedQuizzesCountsStatistics(child));
    }

    private SolvedQuizzesCountsInfo countSolvedQuizzesCountsStatistics(Child child) {
        return educationQueryDao.countSolvedQuizzesCounts(child.getId());
    }

    private SolvedQuizzesTypesInfo countSolvedQuizzesTypesStatistics(Child child) {
        return educationQueryDao.countSolvedQuizzesTypes(child.getId());
    }

    private Child findChild(String nickname, Long parentId) {
        return userQueryService.findChild(parentId, nickname);
    }

    private void checkFairytaleExist(Long fairytaleId) {
        educationRepository.findFairytaleById(fairytaleId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.FAIRYTALE_NOT_FOUND));
    }
}
