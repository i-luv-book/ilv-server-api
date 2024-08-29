package hanium.server.i_luv_book.user.login.dto;

import lombok.Data;


@Data
public class LoginFormResDTO {
    public LoginFormResDTO(String url) {
        this.url = url;
    }

    private String url;
}
