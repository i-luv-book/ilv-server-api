package hanium.server.i_luv_book.domain.auth.util;

import hanium.server.i_luv_book.domain.auth.dto.response.GoogleAccessTokenDTO;
import hanium.server.i_luv_book.domain.auth.dto.response.GoogleUserInfoDTO;
import hanium.server.i_luv_book.domain.auth.dto.response.KakaoAccessTokenDTO;
import hanium.server.i_luv_book.domain.auth.dto.response.KakaoUserInfoDTO;
import hanium.server.i_luv_book.domain.auth.exception.KakaoOauthException;
import hanium.server.i_luv_book.domain.auth.exception.GoogleOauthException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
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

    public Mono<KakaoAccessTokenDTO> getAceessTokenFromKakao(MultiValueMap<String,String> map) {
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

    public Mono<GoogleAccessTokenDTO> getAceesTokenFromGoogle(MultiValueMap<String,String> map) {
        return googleWebClient.post()
                .uri("/token")
                .bodyValue(map)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.error(new GoogleOauthException("인가 코드 받기 4xx 오류, 해당 서비스가 google과 연결중에 문제가 발생했습니다.")))
                .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new GoogleOauthException("인가 코드 받기 5xx 오류, 해당 서비스가 google과 연결중에 문제가 발생했습니다.")))
                .bodyToMono(GoogleAccessTokenDTO.class);
    }
    public Mono<GoogleUserInfoDTO> getUserInfoFromGoogle(String token) {
        return googleWebClient.mutate()
                .baseUrl("https://www.googleapis.com")
                .build()
                .get()
                .uri("/oauth2/v1/userinfo")
                .header("Authorization","Bearer " + token)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.error(new KakaoOauthException("사용자 정보 가져오기 4xx 오류, 해당 서비스가 kakao와 연결중에 문제가 발생했습니다.")))
                .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new KakaoOauthException("사용자 정보 가져오기 5xx 오류, 해당 서비스가 kakao와 연결중에 문제가 발생했습니다.")))
                .bodyToMono(GoogleUserInfoDTO.class);
    }
}





