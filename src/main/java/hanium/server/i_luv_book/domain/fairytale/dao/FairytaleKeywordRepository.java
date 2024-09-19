package hanium.server.i_luv_book.domain.fairytale.dao;

import hanium.server.i_luv_book.domain.fairytale.domain.FairytaleKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * @author Young9
 */
@Repository
public interface FairytaleKeywordRepository extends JpaRepository<FairytaleKeyword,Long> {
}
