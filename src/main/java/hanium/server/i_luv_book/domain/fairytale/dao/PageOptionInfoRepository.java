package hanium.server.i_luv_book.domain.fairytale.dao;

import hanium.server.i_luv_book.domain.fairytale.domain.PageOptionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * @author Young9
 */
@Repository
public interface PageOptionInfoRepository extends JpaRepository<PageOptionInfo,Long> {
}
