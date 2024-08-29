package hanium.server.i_luv_book.user.login.config;

import hanium.server.i_luv_book.user.login.domain.LoginType;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@Slf4j
@Getter
public class LoginWebConfig {

    @Value("${spring.data.kakao.client-secret}")
    private String kakaoClentSecret;

    @Value("${spring.data.kakao.redirect-url}")
    private String kakaoRedirectUrl;

    @Value("${spring.data.kakao.rest-api-key}")
    private String kakaoRestKey;


    // 구글과 카카오에 맞는 로그인 화면을 띄우기 위한 초기화
    @PostConstruct
    public void loginFormUrlInit() {
        LoginType.KAKAO.setLoginFormUrl(getKakaoLoginFormUrl());
        LoginType.GOOGLE.setLoginFormUrl(getGoogleLoginFormUrl());

    }

    private String getGoogleLoginFormUrl() {
        return null;
    }

    private String getKakaoLoginFormUrl() {
        //https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}
        String kakaoLoginTemplate = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=%s&redirect_uri=%s" ;
        return String.format(kakaoLoginTemplate,this.kakaoRestKey,this.kakaoRedirectUrl);
    }




}
