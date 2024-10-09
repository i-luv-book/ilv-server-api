package hanium.server.i_luv_book.domain.education.infra.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author ijin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenAiResponse {

    private String id;
    private String object;
    private Integer created;
    private String model;
    private List<Choice> choices;
    private LinkedHashMap<String, Object> usage;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Choice {
        private int index;
        private OpenAiChatMessage message;
        private Object logprobs;
        private String finish_reason;
    }
}
