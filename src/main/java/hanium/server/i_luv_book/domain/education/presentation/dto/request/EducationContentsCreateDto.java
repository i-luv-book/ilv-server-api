package hanium.server.i_luv_book.domain.education.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class EducationContentsCreateDto {
    @NotBlank(message = "level is blank")
    private Level level;
    @NotBlank(message = "title is blank")
    private String title;
    @NotBlank(message = "content is blank")
    private String content;

    @Getter
    public enum Level {
        LOW("Low-Level"), MEDIUM("Medium-Level"), HIGH("High-Level");

        private final String value;

        Level(String value) {
            this.value = value;
        }
    }
}
