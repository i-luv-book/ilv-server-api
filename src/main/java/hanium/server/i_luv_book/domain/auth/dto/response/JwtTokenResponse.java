package hanium.server.i_luv_book.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * @author Young9
 */
@Data
@AllArgsConstructor
public class JwtTokenResponse {
    private String accessToken;
    private String refreshToken;
}
