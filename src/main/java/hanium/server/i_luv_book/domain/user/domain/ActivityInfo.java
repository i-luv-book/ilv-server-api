package hanium.server.i_luv_book.domain.user.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
public class ActivityInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "acitivity_info_id")
    private long id;
    @Column(name = "login_date")
    private int loginDate;
    @Column(name = "last_login_dat")
    private LocalDate lastLoginDate;
    @Column(name = "fairytale_reading_duration")
    private int fairytaleReadingDuration;
    @Column(name = "quiz_solving_duration")
    private int quizSolvingDuration;

    @Builder
    public ActivityInfo() {
        this.loginDate = 1;
        this.lastLoginDate = LocalDate.now();
        this.fairytaleReadingDuration = 0;
        this.quizSolvingDuration = 0;
    }

    // 동화 본 시간 업데이트
    public void updateFairytaleReadingDuration(int minute) {
        fairytaleReadingDuration += minute;
    }

    // 퀴즈 푼 시간 업데이트
    public void updateQuizSolvingDuration(int minute) {
        quizSolvingDuration += minute;
    }

    // 로그인 날짜 업데이트
    public void updateLoginStatus() {
        if (!Objects.equals(lastLoginDate, LocalDate.now())) {
            loginDate += 1;
            lastLoginDate = LocalDate.now();
        }
    }
}
