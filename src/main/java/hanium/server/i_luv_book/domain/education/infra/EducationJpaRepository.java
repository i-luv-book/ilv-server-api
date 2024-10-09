package hanium.server.i_luv_book.domain.education.infra;

import hanium.server.i_luv_book.domain.education.domain.EducationRepository;
import hanium.server.i_luv_book.domain.education.domain.Quiz;
import hanium.server.i_luv_book.domain.education.domain.Words;
import hanium.server.i_luv_book.domain.fairytale.domain.Fairytale;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class EducationJpaRepository implements EducationRepository {

    private final EntityManager em;

    @Override
    public Long save(Quiz quiz) {
        em.persist(quiz);
        return quiz.getId();
    }

    @Override
    public Long save(Words words) {
        em.persist(words);
        return words.getId();
    }

    @Override
    public boolean checkQuizExistByFairytaleId(Long fairytaleId) {
        Long count = em.createQuery("select count(q) " +
                        "from Quiz q where q.fairytale.id = :fairytaleId", Long.class)
                    .setParameter("fairytaleId", fairytaleId)
                    .getSingleResult();
        return count > 0;
    }

    @Override
    public Optional<Fairytale> findFairytaleById(long fairytaleId) {
        return Optional.ofNullable(em.find(Fairytale.class, fairytaleId));
    }

    @Override
    public Map<Long, Quiz> findQuizzesByFairytaleId(Long fairytaleId) {
        List<Quiz> quizzes =  em.createQuery("select q " +
                        "from Quiz q where q.fairytale.id = :fairytaleId", Quiz.class)
                .setParameter("fairytaleId", fairytaleId)
                .getResultList();

        return quizzes.stream()
                .collect(Collectors.toMap(Quiz::getId, quiz -> quiz));
    }
}
