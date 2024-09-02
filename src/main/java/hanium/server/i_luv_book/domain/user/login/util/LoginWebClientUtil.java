package hanium.server.i_luv_book.domain.user.login.util;

import hanium.server.i_luv_book.domain.user.application.dto.request.ParentCreateCommand;
import hanium.server.i_luv_book.domain.user.domain.Parent;
import hanium.server.i_luv_book.domain.user.domain.Role;
import hanium.server.i_luv_book.domain.user.infra.UserDataJpaRepository;
import hanium.server.i_luv_book.domain.user.login.domain.LoginType;
import hanium.server.i_luv_book.domain.user.login.dto.response.GoogleAccessTokenDTO;
import hanium.server.i_luv_book.domain.user.login.dto.response.JwtTokenResponse;
import hanium.server.i_luv_book.domain.user.login.dto.response.KakaoAccessTokenDTO;
import hanium.server.i_luv_book.domain.user.login.dto.response.KakaoUserInfoDTO;
import hanium.server.i_luv_book.domain.user.login.exception.GoogleOauthException;

import hanium.server.i_luv_book.domain.user.login.exception.KakaoOauthException;
import hanium.server.i_luv_book.global.jwt.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoginWebClientUtil {
    private final WebClient kakaoWebClient;
    private final WebClient googleWebClient;
    public LoginWebClientUtil() {
        this.kakaoWebClient = WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE+";charset=utf-8")
                .baseUrl("https://kauth.kakao.com")
                .build();

        this.googleWebClient = WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .baseUrl("https://oauth2.googleapis.com")
                .build();
    }

    public Mono<KakaoAccessTokenDTO> setMonoForKakao(MultiValueMap<String,String> map) {
        return kakaoWebClient.post()
                .uri("/oauth/token")
                .bodyValue(map)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.error(new KakaoOauthException("인가 코드 받기 4xx 오류, 해당 서비스가 kakao와 연결중에 문제가 발생했습니다.")))
                .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new KakaoOauthException("인가 코드 받기 5xx 오류, 해당 서비스가 kakao와 연결중에 문제가 발생했습니다.")))
                .bodyToMono(KakaoAccessTokenDTO.class);
    }
    public Mono<KakaoUserInfoDTO> getUserInfoFromKakao(String token) {
        return kakaoWebClient.mutate()
                .baseUrl("https://kapi.kakao.com")
                .build()
                .get()
                .uri("/v1/oidc/userinfo")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.error(new KakaoOauthException("사용자 정보 가져오기 4xx 오류, 해당 서비스가 kakao와 연결중에 문제가 발생했습니다.")))
                .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new KakaoOauthException("사용자 정보 가져오기 5xx 오류, 해당 서비스가 kakao와 연결중에 문제가 발생했습니다.")))
                .bodyToMono(KakaoUserInfoDTO.class);

    }

    public void setMonoForGoogle(MultiValueMap<String,String> map) {
               googleWebClient.post()
                .uri("/token")
                .bodyValue(map)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.error(new GoogleOauthException("인가 코드 받기 4xx 오류, 해당 서비스가 google과 연결중에 문제가 발생했습니다.")))
                .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new GoogleOauthException("인가 코드 받기 5xx 오류, 해당 서비스가 google과 연결중에 문제가 발생했습니다.")))
                .bodyToMono(GoogleAccessTokenDTO.class)
                       .subscribe(resonse -> googleTest(resonse));

    }
    public void googleTest(GoogleAccessTokenDTO googleAccessTokenDTO) {
        log.info("요청 완료다요 {}",googleAccessTokenDTO);
    }




    public void getUserInfoFromGoogle(String token) {
        kakaoWebClient.mutate()
                .baseUrl("https://kapi.kakao.com")
                .build()
                .get()
                .uri("/v1/oidc/userinfo")
                .header("Authorization","Bearer " + token)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.error(new KakaoOauthException("사용자 정보 가져오기 4xx 오류, 해당 서비스가 kakao와 연결중에 문제가 발생했습니다.")))
                .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new KakaoOauthException("사용자 정보 가져오기 5xx 오류, 해당 서비스가 kakao와 연결중에 문제가 발생했습니다.")))
                .bodyToMono(KakaoUserInfoDTO.class)
                .subscribe(response -> test(response));
    }
    private KakaoUserInfoDTO test(KakaoUserInfoDTO kakaoUserInfoDTO) {
        log.info(kakaoUserInfoDTO.toString());
        return kakaoUserInfoDTO;
    }

}





