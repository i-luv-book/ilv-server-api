package hanium.server.i_luv_book.user.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.time.LocalDate;

@Embeddable
@Getter
public class LoginStatus {
    private int consecutiveLoginDays;
    private LocalDate lastLoginDate;

    public LoginStatus() {
        this.consecutiveLoginDays = 1;
        this.lastLoginDate = LocalDate.now();
    }
}
