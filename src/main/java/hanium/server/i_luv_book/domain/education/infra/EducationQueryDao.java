package hanium.server.i_luv_book.domain.education.infra;

import hanium.server.i_luv_book.domain.education.application.dto.response.SolvedQuizzesCountsInfo;
import hanium.server.i_luv_book.domain.education.application.dto.response.SolvedQuizzesTypesInfo;
import hanium.server.i_luv_book.domain.education.domain.QuizInfo;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static hanium.server.i_luv_book.domain.education.domain.QuizInfo.*;

@Repository
@RequiredArgsConstructor
public class EducationQueryDao {

    private final EntityManager em;

    // 퀴즈의 유형별로 카운팅
    public SolvedQuizzesTypesInfo countSolvedQuizzesTypes(Long childId) {
        List<Object[]> results = em.createQuery("select q.quizInfo.quizType, count(q) " +
                        "from Quiz q " +
                        "join q.fairytale f " +
                        "join f.child c " +
                        "where c.id = :childId " +
                        "group by q.quizInfo.quizType", Object[].class)
                .setParameter("childId", childId)
                .getResultList();

        Map<QuizType, Long> quizTypeCounts = new HashMap<>();
        for (Object[] result : results) {
            quizTypeCounts.put((QuizType) result[0], (Long) result[1]);
        }

        return new SolvedQuizzesTypesInfo(quizTypeCounts);
    }

    // 퀴즈 정답 개수 조회
    public SolvedQuizzesCountsInfo countSolvedQuizzesCounts(Long childId) {
        return em.createQuery("select new hanium.server.i_luv_book.domain.education.application.dto.response.SolvedQuizzesCountsInfo(" +
                        "sum(case when q.answerInfo.isCorrect = true then 1 else 0 end), " +
                        "sum(case when q.answerInfo.isCorrect = false then 1 else 0 end)) " +
                        "from Quiz q " +
                        "join q.fairytale f " +
                        "join f.child c " +
                        "where c.id = :childId", SolvedQuizzesCountsInfo.class)
                .setParameter("childId", childId)
                .getSingleResult();
    }
}
