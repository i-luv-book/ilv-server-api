package hanium.server.i_luv_book.user.application.dto.request;

import java.time.LocalDate;

import static hanium.server.i_luv_book.user.domain.Child.*;

/**
 * @author ijin
 */
public record ChildCreateCommand(String nickname, LocalDate birthDate, Gender gender, long parentId) {
}
