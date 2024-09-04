package hanium.server.i_luv_book.domain.auth.domain;


import org.springframework.data.redis.core.RedisHash;


//TTL을 2주로 설정 86400초는 1일을 나타냄
@RedisHash(value = "refreshToken", timeToLive = 86400*14)
public class RefreshToken {




}
