package hanium.server.i_luv_book.user.presentation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDate;

import static hanium.server.i_luv_book.user.domain.Child.*;

/**
 * @author ijin
 */
@Getter
public class ChildCreateDto {
    @NotBlank(message = "nickname is blank")
    private String nickname;
    @NotBlank(message = "birthdate is invalid")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate birthDate;
    @NotBlank(message = "gender is blank")
    private Gender gender;
    private String imgUrl;
}
