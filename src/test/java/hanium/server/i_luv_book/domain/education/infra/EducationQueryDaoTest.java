package hanium.server.i_luv_book.domain.education.infra;

import hanium.server.i_luv_book.domain.education.application.dto.response.FairytaleQuizzesInfo;
import hanium.server.i_luv_book.domain.education.application.dto.response.QuizDetailInfo;
import hanium.server.i_luv_book.domain.education.application.dto.response.SolvedQuizzesCountsInfo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class EducationQueryDaoTest {

    @Autowired
    EducationQueryDao educationQueryDao;

    @Test
    @Transactional
    void countSolvedQuizzesTypes() {
        educationQueryDao.countSolvedQuizzesTypes(1L);
    }

    @Test
    @Transactional
    void countSolvedQuizzesCounts() {
        SolvedQuizzesCountsInfo solvedQuizzesCountsInfo = educationQueryDao.countSolvedQuizzesCounts(1L);
        System.out.println(solvedQuizzesCountsInfo.getCorrectQuizzes());
        System.out.println(solvedQuizzesCountsInfo.getWrongQuizzes());
    }

    @Test
    @Transactional
    void findFairytaleQuizzesInfo() {
        List<FairytaleQuizzesInfo> fairytaleQuizzesInfos = educationQueryDao.findFairytaleQuizzesInfo(11L, 1L);
        for (FairytaleQuizzesInfo info : fairytaleQuizzesInfos) {
            System.out.println(info.getFairytaleId() + " : " + info.getTitle() + ", " + info.getCorrectQuizzesCount());
        }
    }

    @Test
    @Transactional
    void findSolvedFairytaleQuizzesInfo() {
        List<QuizDetailInfo> fairytaleQuizzesInfos = educationQueryDao.findQuizzesDetailInfo(1L);
        for (QuizDetailInfo info : fairytaleQuizzesInfos) {
            System.out.println(info.toString());
        }
    }
}
