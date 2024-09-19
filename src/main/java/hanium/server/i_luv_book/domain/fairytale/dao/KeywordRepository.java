package hanium.server.i_luv_book.domain.fairytale.dao;

import hanium.server.i_luv_book.domain.fairytale.domain.Keyword;
import hanium.server.i_luv_book.domain.fairytale.domain.enums.KeywordCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
/**
 * @author Young9
 */
@Repository
public interface KeywordRepository extends JpaRepository<Keyword,Long> {

    Optional<Keyword> findByContentAndKeywordCategory(String content, KeywordCategory keywordCategory);
}
