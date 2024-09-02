package hanium.server.i_luv_book.domain.user.login.config;

import hanium.server.i_luv_book.domain.user.login.domain.LoginType;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

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

    @Value("${spring.data.google.client-id}")
    private String googleClientId;

    @Value("${spring.data.google.client-secret}")
    private String googleClientSecret;

    @Value("${spring.data.google.redirect-url}")
    private String googleRedirectUrl;



    // 구글과 카카오에 맞는 로그인 화면을 띄우기 위한 초기화
    @PostConstruct
    public void loginFormUrlInit() {
        LoginType.KAKAO.setLoginFormUrl(getKakaoLoginFormUrl());
        LoginType.GOOGLE.setLoginFormUrl(getGoogleLoginFormUrl());

    }

    private String getGoogleLoginFormUrl() {
        // 구글 인가코드 url 받기
        //https://accounts.google.com/o/oauth2/auth?scope=https://www.googleapis.com/auth/androidpublisher&response_type=code&access_type=offline&redirect_uri=...&client_id=...
        String googleLoginTemplate = "https://accounts.google.com/o/oauth2/auth?scope=https://www.googleapis.com/auth/androidpublisher&response_type=code&access_type=offline&redirect_uri=%s&client_id=%s";

        return String.format(googleLoginTemplate,this.googleRedirectUrl,this.googleClientId);
    }

    private String getKakaoLoginFormUrl() {
        // 카카오 인가코드 url 받기
        //https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}
        String kakaoLoginTemplate = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=%s&redirect_uri=%s" ;

        return String.format(kakaoLoginTemplate,this.kakaoRestKey,this.kakaoRedirectUrl);
    }




}
