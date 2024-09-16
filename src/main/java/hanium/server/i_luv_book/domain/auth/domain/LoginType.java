package hanium.server.i_luv_book.domain.auth.domain;

import lombok.Getter;

@Getter
public enum LoginType {
    GOOGLE,
    KAKAO;

    private String loginFormUrl;
    public void setLoginFormUrl(String loginFormUrl) {
        this.loginFormUrl = loginFormUrl;
    }
}
