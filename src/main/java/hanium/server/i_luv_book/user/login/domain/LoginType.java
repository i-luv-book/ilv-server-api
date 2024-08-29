package hanium.server.i_luv_book.user.login.domain;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;

@Getter

public enum LoginType {
    GOOGLE,
    KAKAO;

    private String loginFormUrl;
    public void setLoginFormUrl(String loginFormUrl) {
        this.loginFormUrl = loginFormUrl;
    }
}
