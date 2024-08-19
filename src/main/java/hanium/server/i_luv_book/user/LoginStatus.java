package hanium.server.i_luv_book.user;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class LoginStatus {
    private int consecutiveLoginDays;
    private LocalDate lastLoginDate;

    @Builder
    public LoginStatus(LocalDate lastLoginDate) {
        this.consecutiveLoginDays = 1;
        this.lastLoginDate = lastLoginDate;
    }
}
