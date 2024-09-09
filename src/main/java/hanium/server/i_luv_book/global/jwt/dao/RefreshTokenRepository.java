package hanium.server.i_luv_book.global.jwt.dao;

import hanium.server.i_luv_book.global.jwt.domain.RefreshToken;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken,String> {

    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByUuid(String uuid);
    List<RefreshToken> findAllByUserId(String userId);
    default void deleteByUuid(String uuid) {
        findByUuid(uuid).ifPresent(this::delete);
    }

}
