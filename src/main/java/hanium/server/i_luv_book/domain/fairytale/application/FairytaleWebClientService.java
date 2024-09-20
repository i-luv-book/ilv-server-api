package hanium.server.i_luv_book.domain.fairytale.application;

import hanium.server.i_luv_book.domain.fairytale.dto.request.*;
import hanium.server.i_luv_book.domain.fairytale.dto.response.GameFairyTaleResponseDTO;
import hanium.server.i_luv_book.domain.fairytale.dto.response.GeneralFairyTaleResponseDTO;
import hanium.server.i_luv_book.domain.fairytale.exception.FairytaleCreateException;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
/**
 * @author Young9
 */
public class FairytaleWebClientService {


    // 일반형 동화 생성을 위한 Mono
    public Mono<GeneralFairyTaleResponseDTO> createGeneralTale(GeneralFairyTaleRequestDTO taleRequestDTO){
        WebClient webClient = getWebClient();
        GeneralFairyTaleBody body = new GeneralFairyTaleBody(taleRequestDTO.getKeywords());

        return webClient.post()
                .uri(taleRequestDTO.getDifficulty().getGeneralUrl())
                .body(Mono.just(body), GeneralFairyTaleBody.class)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.error(new FairytaleCreateException("동화 생성하기 4xx 오류, 동화 생성 서버와 연결중에 문제가 발생했습니다.")))
                .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new FairytaleCreateException("동화 생성하기 5xx 오류, 동화 생성 서버와 연결중에 문제가 발생했습니다.")))
                .bodyToMono(GeneralFairyTaleResponseDTO.class);
    }

    //게임형 동화 생성울 위한 Mono, 게임형 동화는 동화 생성시 페이지가 3개라면 동화 마무리를 위한 url 호출이 필요함.
    public Mono<GameFairyTaleResponseDTO> createGameTale(GameFairyTaleRequestDTO taleRequestDTO, String url, String fairytale){
        WebClient webClient = getWebClient();
        GameFairyTaleBody body = new GameFairyTaleBody(taleRequestDTO.getKeywords(), fairytale);

        return webClient.post()
                .uri(url)
                .body(Mono.just(body), GameFairyTaleBody.class)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.error(new FairytaleCreateException("동화 생성하기 4xx 오류, 동화 생성 서버와 연결중에 문제가 발생했습니다.")))
                .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new FairytaleCreateException("동화 생성하기 5xx 오류, 동화 생성 서버와 연결중에 문제가 발생했습니다.")))
                .bodyToMono(GameFairyTaleResponseDTO.class);
    }

    public <T> Mono<T> createTale(Class<T> responseType,Object body, String url){
        WebClient webClient = getWebClient();

        return webClient.post()
                .uri(url)
                .bodyValue(body)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.error(new FairytaleCreateException("동화 생성하기 4xx 오류, 동화 생성 서버와 연결중에 문제가 발생했습니다.")))
                .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new FairytaleCreateException("동화 생성하기 5xx 오류, 동화 생성 서버와 연결중에 문제가 발생했습니다.")))
                .bodyToMono(responseType);
    }

    private WebClient getWebClient() {
        return WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE,MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Data
    static public class GameFairyTaleBody {
        private KeywordsDTO keywords;
        private String fairytale;
        public GameFairyTaleBody(KeywordsDTO keywords, String fairytale) {
            this.keywords = keywords;
            this.fairytale = fairytale;
        }
    }

    @Data
    static public class GeneralFairyTaleBody {
        //동화 생성시 동화 서버로 보낼 body 스펙 정의
        private KeywordsDTO keywords;
        public GeneralFairyTaleBody(KeywordsDTO keywordsDTO) {
            this.keywords = keywordsDTO;
        }
    }


}
