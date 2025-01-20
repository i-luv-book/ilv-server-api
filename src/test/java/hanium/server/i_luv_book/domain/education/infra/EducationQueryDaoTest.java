package hanium.server.i_luv_book.domain.education.infra;

import hanium.server.i_luv_book.domain.education.application.dto.response.*;
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
    void findWordDetailInfos() {
        List<WordDetailInfo> wordDetailInfos = educationQueryDao.findWordDetailInfo(2L);
        System.out.println(wordDetailInfos);
    }

    @Test
    @Transactional
    void findFairytaleWordsInfo() {
        // 1L
        List<FairytaleWordsInfo> wordsInfos1 = educationQueryDao.findFairytaleWordsInfo(0L, 1L);
        System.out.println(wordsInfos1.toString());

        // 5L
        List<FairytaleWordsInfo> wordsInfo2 = educationQueryDao.findFairytaleWordsInfo(5L, 1L);
        System.out.println(wordsInfo2.toString());
    }

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
