package hanium.server.i_luv_book.domain.user.application.dto.request;

import java.time.LocalDate;

import static hanium.server.i_luv_book.domain.user.domain.Child.*;

/**
 * @author ijin
 */
public record ChildCreateCommand(String nickname, LocalDate birthDate, Gender gender, long parentId) {
}
