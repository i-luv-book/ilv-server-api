package hanium.server.i_luv_book.domain.education.infra;

import hanium.server.i_luv_book.domain.education.domain.Quiz;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EducationJpaRepositoryTest {

    @Autowired
    EducationJpaRepository repository;

    @Test
    @Transactional(readOnly = true)
    void findQuizzesByFairytaleId() {
        Map<Long, Quiz> quizMap = repository.findQuizzesByFairytaleId(1L);
        System.out.println(quizMap.toString());
    }

    @Test
    @Transactional(readOnly = true)
    void countWordsByFairytaleId() {
        System.out.println(repository.countWordsByChildId(1L));
    }
}
