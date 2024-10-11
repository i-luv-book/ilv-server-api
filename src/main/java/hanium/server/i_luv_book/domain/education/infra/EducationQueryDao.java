package hanium.server.i_luv_book.domain.education.infra;

import hanium.server.i_luv_book.domain.education.application.dto.response.*;
import hanium.server.i_luv_book.domain.education.domain.QuizInfo;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static hanium.server.i_luv_book.domain.education.domain.QuizInfo.*;

@Repository
@RequiredArgsConstructor
public class EducationQueryDao {

    private final EntityManager em;

    // 단어장 리스트 조회
    public List<FairytaleWordsInfo> findFairytaleWordsInfo(Long cursorFairytaleId, Long childId) {
        return em.createQuery("select distinct new hanium.server.i_luv_book.domain.education.application.dto.response.FairytaleWordsInfo" +
                        "(f.id, f.title)" +
                        "from Fairytale f " +
                        "join f.words w " +
                        "join f.child c " +
                        "where f.id > :cursorFairytaleId and f.child.id = :childId " +
                        "order by f.id", FairytaleWordsInfo.class)
                .setParameter("cursorFairytaleId", cursorFairytaleId)
                .setParameter("childId", childId)
                .setMaxResults(10)
                .getResultList();
    }

    // 단어장 조회
    public List<WordDetailInfo> findWordDetailInfo(Long fairytaleId) {
        return  em.createQuery("select new hanium.server.i_luv_book.domain.education.application.dto.response.WordDetailInfo" +
                "(w.id, w.word, w.translation)" +
                "from Words w " +
                "where w.fairytale.id = :fairytaleId " +
                "order by w.id", WordDetailInfo.class)
                .setParameter("fairytaleId", fairytaleId)
                .getResultList();
    }

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

    // 퀴즈 리스트 조회
    public List<FairytaleQuizzesInfo> findFairytaleQuizzesInfo(Long cursorFairytaleId, Long childId) {
        return em.createQuery("select new hanium.server.i_luv_book.domain.education.application.dto.response.FairytaleQuizzesInfo" +
                        "(f.id, f.title, sum(case when q.answerInfo.isCorrect = true then 1 else 0 end), f.level)" +
                        "from Fairytale f " +
                        "join f.quizzes q " +
                        "join f.child c " +
                        "where f.id > :cursorFairytaleId and f.child.id = :childId " +
                        "group by f.id, f.title, f.level " +
                        "order by f.id", FairytaleQuizzesInfo.class)
                .setParameter("cursorFairytaleId", cursorFairytaleId)
                .setParameter("childId", childId)
                .setMaxResults(10)
                .getResultList();
    }

    // 퀴즈 상세조회
    public List<QuizDetailInfo> findQuizzesDetailInfo(Long fairytaleId) {
        List<QuizDetailInfo> quizDetailInfos = findQuizzesDetailInfos(fairytaleId);
        List<QuizOptionsInfo> quizOptionsInfos = findQuizzesOptionsInfo(toQuizzesIds(quizDetailInfos));
        return matchQuizOptionsToDetailInfos(quizDetailInfos, quizOptionsInfos);
    }

    private List<QuizDetailInfo> matchQuizOptionsToDetailInfos(List<QuizDetailInfo> quizDetailInfos, List<QuizOptionsInfo> quizOptionsInfos) {
        for (QuizDetailInfo quizDetailInfo : quizDetailInfos) {
            for (QuizOptionsInfo quizOptionsInfo : quizOptionsInfos) {
                if (quizDetailInfo.getQuizId().equals(quizOptionsInfo.getQuizId())) {
                    quizDetailInfo.setQuizOptions(quizOptionsInfo);
                    break;
                }
            }
        }

        return quizDetailInfos;
    }

    private List<QuizDetailInfo> findQuizzesDetailInfos(Long fairytaleId) {
        return em.createQuery("select new hanium.server.i_luv_book.domain.education.application.dto.response.QuizDetailInfo" +
                        "(q.id, q.quizInfo.quizType, q.quizInfo.format, q.quizInfo.question, q.quizInfo.pronounOrWord, " +
                        "q.answerInfo.answer, q.answerInfo.childAnswer, q.answerInfo.isCorrect)" +
                        "from Quiz q " +
                        "join q.fairytale f " +
                        "where f.id = :fairytaleId " +
                        "order by q.id", QuizDetailInfo.class)
                .setParameter("fairytaleId", fairytaleId)
                .getResultList();
    }

    private List<Long> toQuizzesIds(List<QuizDetailInfo> quizDetailInfos) {
        return quizDetailInfos.stream()
                .map(QuizDetailInfo::getQuizId).toList();
    }

    private List<QuizOptionsInfo> findQuizzesOptionsInfo(List<Long> quizzesIds) {
        return em.createQuery("select new hanium.server.i_luv_book.domain.education.application.dto.response.QuizOptionsInfo" +
                        "(q.id, o.optionA, o.optionB, o.optionC, o.optionD)" +
                        "from Quiz q " +
                        "join q.options o " +
                        "where q.id in :quizzesIds", QuizOptionsInfo.class)
                .setParameter("quizzesIds", quizzesIds)
                .getResultList();
    }
}
