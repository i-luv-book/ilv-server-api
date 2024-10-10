package hanium.server.i_luv_book.domain.education.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WordCreateCommand {
    private String voca;
    private String translation;
}
