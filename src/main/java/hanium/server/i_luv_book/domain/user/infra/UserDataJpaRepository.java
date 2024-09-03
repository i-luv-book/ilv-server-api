package hanium.server.i_luv_book.domain.user.infra;

import hanium.server.i_luv_book.domain.user.domain.Parent;
import hanium.server.i_luv_book.domain.auth.domain.LoginType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDataJpaRepository extends CrudRepository<Parent,Long> {
    Optional<Parent> findBySocialIdAndLoginType(String socialId, LoginType loginType);
}
