package hanium.server.i_luv_book.domain.user.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * @author ijin
 */
@Getter
public class NotificationInfoDto {
    @NotBlank(message = "nickname is blank")
    private String nickname;
    @NotBlank(message = "fcmToken is blank")
    private String fcmToken;
}
