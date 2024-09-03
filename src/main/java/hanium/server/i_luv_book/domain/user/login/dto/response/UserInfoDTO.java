package hanium.server.i_luv_book.domain.user.login.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoDTO {
    private String socialId;
    private String email;
}
