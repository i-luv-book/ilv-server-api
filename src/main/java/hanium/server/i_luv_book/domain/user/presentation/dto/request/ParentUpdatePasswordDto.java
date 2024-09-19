package hanium.server.i_luv_book.domain.user.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * @author ijin
 */
@Getter
public class ParentUpdatePasswordDto {
    @NotBlank(message = "password is blank")
    private String password;
}
