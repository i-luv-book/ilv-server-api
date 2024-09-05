package hanium.server.i_luv_book.global.jwt.domain;


import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.Date;
import java.util.UUID;


//TTL을 2주로 설정 86400초는 1일을 나타냄
@Getter
@RedisHash(timeToLive = 86400*14)
public class RefreshToken {

    @Id
    private String id;
    //모든기기에서 로그아웃, 한 계정당 몇 개의 기기에 연결할 수 있는지 등 확장성을 위한 필드
    @Indexed
    private String userId;

    @Indexed
    private String token;

    @Indexed
    private String uuid;

    public RefreshToken(String userId, String token, String uuid) {
        this.userId = userId;
        this.token = token;
        this.uuid = uuid;
    }
}
