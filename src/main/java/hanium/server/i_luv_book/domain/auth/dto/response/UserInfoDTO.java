package hanium.server.i_luv_book.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * @author Young9
 */
@Getter
@AllArgsConstructor
public class UserInfoDTO {
    private String socialId;
    private String email;
}
