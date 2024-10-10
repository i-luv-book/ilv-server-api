package hanium.server.i_luv_book.domain.education.infra;

import hanium.server.i_luv_book.domain.education.application.AsyncEducationCommandService;
import hanium.server.i_luv_book.domain.education.application.dto.request.EducationContentsCreateCommand;
import hanium.server.i_luv_book.domain.education.application.dto.request.QuizCreateCommand;
import hanium.server.i_luv_book.domain.education.application.dto.request.WordCreateCommand;
import hanium.server.i_luv_book.domain.education.domain.EducationPrompts;
import hanium.server.i_luv_book.domain.education.domain.EducationContentsGenerator;
import hanium.server.i_luv_book.domain.education.infra.dto.OpenAiChatMessage;
import hanium.server.i_luv_book.domain.education.infra.dto.OpenAiResponse;
import hanium.server.i_luv_book.domain.education.infra.dto.OpenAiResponseProcessor;
import hanium.server.i_luv_book.global.exception.BusinessException;
import hanium.server.i_luv_book.global.exception.code.ErrorCode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EducationGptClient implements EducationContentsGenerator {

    private final WebClient webClient;
    private final AsyncEducationCommandService asyncEducationCommandService;

    public EducationGptClient(@Qualifier("EducationOpenAiWebClient") WebClient webClient,
                              AsyncEducationCommandService asyncEducationCommandService) {
        this.webClient = webClient;
        this.asyncEducationCommandService = asyncEducationCommandService;
    }

    @Value("${openai.api.url}")
    private String openAiRequestUrl;
    @Value("${openai.api.model}")
    private String openAiModel;

    @Async("educationTaskExecutor")
    @Override
    public void generateQuizzes(EducationContentsCreateCommand command, Long fairytaleId) {
        List<OpenAiChatMessage> prompts = processQuizPrompts(command);
        Map<String, Object> openAiRequestMessages = processOpenAiRequestMessages(prompts);
        webClient
                .post()
                .uri(openAiRequestUrl)
                .bodyValue(openAiRequestMessages)
                .retrieve()
                .bodyToMono(OpenAiResponse.class)
                .flatMap(gptResponse -> {
                    List<String> contents = gptResponse.getChoices().stream()
                            .map(choice -> choice.getMessage().getContent())
                            .toList();
                    return Mono.just(contents.get(0));
                })
                .retry(3)
                .subscribe(
                        response -> {
                            List<QuizCreateCommand> commands = OpenAiResponseProcessor.toQuizCreateCommands(response);
                            asyncEducationCommandService.saveQuizzes(commands, fairytaleId);
                        },
                        error -> {
                            throw new BusinessException(ErrorCode.FAILED_OPENAI_REQUEST, error.getMessage());
                        }
                );
    }

    @Async("educationTaskExecutor")
    @Override
    public void generateWords(EducationContentsCreateCommand command, Long fairytaleId) {
        List<OpenAiChatMessage> prompts = processWordPrompts(command);
        Map<String, Object> openAiRequestMessages = processOpenAiRequestMessages(prompts);
        webClient
                .post()
                .uri(openAiRequestUrl)
                .bodyValue(openAiRequestMessages)
                .retrieve()
                .bodyToMono(OpenAiResponse.class)
                .flatMap(gptResponse -> {
                    List<String> contents = gptResponse.getChoices().stream()
                            .map(choice -> choice.getMessage().getContent())
                            .toList();
                    return Mono.just(contents.get(0));
                })
                .retry(3)
                .subscribe(
                        response -> {
                            List<WordCreateCommand> commands = OpenAiResponseProcessor.toWordCreateCommands(response);
                            asyncEducationCommandService.saveWords(commands, fairytaleId);
                        },
                        error -> {
                            throw new BusinessException(ErrorCode.FAILED_OPENAI_REQUEST, error.getMessage());
                        }
                );
    }

    private List<OpenAiChatMessage> processWordPrompts(EducationContentsCreateCommand command) {
        List<OpenAiChatMessage> messages = new ArrayList<>();
        messages.add(new OpenAiChatMessage("system", EducationPrompts.WORD_SYSTEM_PROMPT));
        messages.add(new OpenAiChatMessage("user", processWordUserPrompt(command.level(), command.title(), command.content())));
        return messages;
    }

    private String processWordUserPrompt(String level, String title, String content) {
        return "Recommend 10 words based on following English-Fairytale and Level of Quiz.\n" +
                "1. Level of Quiz : " + level + "\n" +
                "2. English Tale\n" +
                "- Title: " + title + "\n" +
                "- Content: " + content;
    }

    private List<OpenAiChatMessage> processQuizPrompts(EducationContentsCreateCommand command) {
        List<OpenAiChatMessage> messages = new ArrayList<>();
        messages.add(new OpenAiChatMessage("system", EducationPrompts.QUIZ_SYSTEM_PROMPT));
        messages.add(new OpenAiChatMessage("user", processQuizUserPrompt(command.level(), command.title(), command.content())));
        return messages;
    }

    private String processQuizUserPrompt(String level, String title, String content) {
        return "Create 10 quizzes based on following English-Fairytale and Level of Quiz.\n" +
                "1. Level of Quiz : " + level + "\n" +
                "2. English Tale\n" +
                "- Title: " + title + "\n" +
                "- Content: " + content;
    }

    private Map<String, Object> processOpenAiRequestMessages(List<OpenAiChatMessage> messages) {
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("model", openAiModel);
        bodyMap.put("stream", false);
        bodyMap.put("messages", messages);
        bodyMap.put("temperature", 1.0);
        return bodyMap;
    }
}
