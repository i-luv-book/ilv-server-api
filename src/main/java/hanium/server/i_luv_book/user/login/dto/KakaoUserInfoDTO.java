package hanium.server.i_luv_book.user.login.dto;

import lombok.Data;

@Data
public class KakaoUserInfoDTO {
    private String sub;
    private String email;
    private boolean email_verified;
}
