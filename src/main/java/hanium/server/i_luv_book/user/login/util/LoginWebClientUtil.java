package hanium.server.i_luv_book.user.login.util;

import hanium.server.i_luv_book.user.login.dto.KakaoAccessTokenDTO;
import hanium.server.i_luv_book.user.login.dto.KakaoUserInfoDTO;
import hanium.server.i_luv_book.user.login.exception.KakaoApiException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.print.attribute.standard.Media;
import java.util.Map;

@Component
public class LoginWebClientUtil {
    private WebClient webClient;
    public LoginWebClientUtil() {
        this.webClient = WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE+";charset=utf-8")
                .baseUrl("https://kauth.kakao.com")
                .build();
    }

    public Mono<KakaoAccessTokenDTO> setMonoForKakao(MultiValueMap<String,String> map) {
        return webClient.post()
                .uri("/oauth/token")
                .bodyValue(map)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.error(new KakaoApiException("인가 코드 받기 4xx 오류, 해당 서비스가 kakao와 연결중에 문제가 발생했습니다.")))
                .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new KakaoApiException("인가 코드 받기 5xx 오류, 해당 서비스가 kakao와 연결중에 문제가 발생했습니다.")))
                .bodyToMono(KakaoAccessTokenDTO.class);
    }

    public void getUserInfoFromKakao(String token) {
        webClient.get()
                .uri("v1/oidc/userinfo")
                .header("Authorization","Bearer" + token)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.error(new KakaoApiException("사용자 정보 가져오기 4xx 오류, 해당 서비스가 kakao와 연결중에 문제가 발생했습니다.")))
                .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new KakaoApiException("사용자 정보 가져오기 5xx 오류, 해당 서비스가 kakao와 연결중에 문제가 발생했습니다.")))
                .bodyToMono(KakaoUserInfoDTO.class);
                //.subscribe(response -> {requestKakaoToken();});
    }




}
