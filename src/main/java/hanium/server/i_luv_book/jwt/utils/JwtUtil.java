package hanium.server.i_luv_book.jwt.utils;

import hanium.server.i_luv_book.jwt.domain.RefreshToken;
import hanium.server.i_luv_book.jwt.repository.RefreshTokenRepository;
import hanium.server.i_luv_book.security.exception.jwt.exception.CustomExpiredJwtException;
import hanium.server.i_luv_book.security.exception.jwt.exception.InvalidJwtException;
import hanium.server.i_luv_book.user.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
/**
 * @author Young9
 */
@Component
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${spring.data.jwt.secret-key}")
    private String secret;

    @Value("${spring.data.jwt.access-token-expiration}")
    private int accessTokenExpiration;

    @Value("${spring.data.jwt.refresh-token-expiration}")
    private int refreshTokenExpiration;

    private final RefreshTokenRepository refreshTokenRepository;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateAccessToken(Long userId, Role role) {
        Date now = new Date();

        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("role",role.name())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenExpiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(Long userId) {
        Date now = new Date();

        String refreshToken = Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenExpiration))
                .signWith(getSigningKey(),SignatureAlgorithm.HS256)
                .compact();

        refreshTokenRepository.save(new RefreshToken(userId,refreshToken));

        return refreshToken;
    }

    public boolean validateToken(String token,Long userId) {
        Long tokenUserId = getUserIdFromToken(token);
        return (tokenUserId.equals(userId) && !isTokenExpired(token));
    }

    public Role getRoleFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return Role.valueOf(claims.get("role",String.class));
    }

    public Long getUserIdFromToken(String token) {
        return Long.valueOf(getAllClaimsFromToken(token).getSubject());
    }

    private boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    private Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

        } catch (ExpiredJwtException e) {
            throw new CustomExpiredJwtException("만료된 토큰입니다.");
        } catch (Exception e) {
            throw new InvalidJwtException("유효하지 않은 토큰입니다.");
        }
    }



}
