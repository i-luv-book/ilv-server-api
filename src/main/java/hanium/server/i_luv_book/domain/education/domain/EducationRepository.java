package hanium.server.i_luv_book.domain.education.domain;

import hanium.server.i_luv_book.domain.fairytale.domain.Fairytale;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EducationRepository {
    Long save(Quiz quiz);

    Long save(Words words);

    boolean checkQuizExistByFairytaleId(Long fairytaleId);

    Optional<Fairytale> findFairytaleById(long fairytaleId);

    Map<Long, Quiz> findQuizzesByFairytaleId(Long fairytaleId);

}
