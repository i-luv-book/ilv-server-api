package hanium.server.i_luv_book.domain.education.infra;

import hanium.server.i_luv_book.domain.education.application.dto.response.SolvedQuizzesCountsInfo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}
