package hanium.server.i_luv_book.jwt.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
/**
 * @author Young9
 */
@RedisHash(value = "token",timeToLive = 10000)
@AllArgsConstructor
@Getter
public class RefreshToken {

    @Id
    private Long id;
    private String refreshToken;

}
