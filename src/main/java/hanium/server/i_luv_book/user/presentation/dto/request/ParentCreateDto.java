package hanium.server.i_luv_book.user.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

/**
 * @author ijin
 */
@Getter
public class ParentCreateDto {
    @NotBlank(message = "email is blank")
    private String email;
    @NotBlank(message = "password is blank")
    private String password;
}
