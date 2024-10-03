package hanium.server.i_luv_book.global.config.openai;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class OpenAiConfig {

    @Value("${openai.api.end-point}")
    private String openAiEndPoint;
    @Value("${openai.api.education-key}")
    private String openAiEducationKey;

    @Qualifier("EducationOpenAiWebClient")
    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(openAiEndPoint)
                .defaultHeader("Content-Type", "application/json;charset=utf-8")
                .defaultHeader("Authorization", "Bearer " + openAiEducationKey)
                .build();
    }
}
