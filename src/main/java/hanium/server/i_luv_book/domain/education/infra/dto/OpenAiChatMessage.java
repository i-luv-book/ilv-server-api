package hanium.server.i_luv_book.domain.education.infra.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class OpenAiChatMessage implements Serializable {

    private String role;
    private String content;

    @Builder
    public OpenAiChatMessage(String role, String content) {
        this.role = role;
        this.content = content;
    }
}
