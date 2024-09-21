package hanium.server.i_luv_book.domain.user.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

/**
 * @author ijin
 */
@Getter
public class ChildActivityDto {
    @NotBlank(message = "nickname is blank")
    private String nickname;
    @NotNull(message = "minute is null")
    private int minute;
}
