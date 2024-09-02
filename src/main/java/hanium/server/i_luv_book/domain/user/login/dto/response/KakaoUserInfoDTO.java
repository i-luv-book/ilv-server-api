package hanium.server.i_luv_book.domain.user.login.dto.response;

import lombok.Data;

@Data
public class KakaoUserInfoDTO {
    private String sub;
    private String email;
    private boolean email_verified;
}
