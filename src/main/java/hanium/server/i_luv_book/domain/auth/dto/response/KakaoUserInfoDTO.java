package hanium.server.i_luv_book.domain.auth.dto.response;

import lombok.Data;
/**
 * @author Young9
 */
@Data
public class KakaoUserInfoDTO {
    private String sub;
    private String email;
    private boolean email_verified;
}
