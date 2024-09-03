package hanium.server.i_luv_book.domain.auth.dto.response;

import lombok.Data;


@Data
public class LoginFormResDTO {
    public LoginFormResDTO(String url) {
        this.url = url;
    }

    private String url;
}
