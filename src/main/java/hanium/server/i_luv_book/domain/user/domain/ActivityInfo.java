package hanium.server.i_luv_book.domain.user.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static hanium.server.i_luv_book.domain.user.domain.Badge.*;

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

    // 동화 본 시간 업데이트 및 배지 부여
    public List<BadgeType> updateFairytaleReadingDuration(int minute) {
        List<BadgeType> grantedBadges = new ArrayList<>();
        int beforeFairytaleReadingDuration = fairytaleReadingDuration;
        int afterFairytaleReadingDuration = fairytaleReadingDuration + minute;

        // 30분 배지 부여
        if (beforeFairytaleReadingDuration < 30 && afterFairytaleReadingDuration >= 30) {
            grantedBadges.add(BadgeType.THIRTY_MINUTES_READ);
        }

        // 60분 배지 부여
        if (beforeFairytaleReadingDuration < 60 && afterFairytaleReadingDuration >= 60) {
            grantedBadges.add(BadgeType.ONE_HOUR_READ);
        }

        // 동화 읽기 시간을 업데이트
        this.fairytaleReadingDuration = afterFairytaleReadingDuration;
        return grantedBadges;
    }

    // 퀴즈 푼 시간 업데이트 및 배지 부여
    public List<BadgeType> updateQuizSolvingDuration(int minute) {
        List<BadgeType> grantedBadges = new ArrayList<>();
        int beforeQuizSolvingDuration = quizSolvingDuration;
        int afterQuizSolvingDuration = quizSolvingDuration + minute;

        // 30분 배지 부여
        if (beforeQuizSolvingDuration < 30 && afterQuizSolvingDuration >= 30) {
            grantedBadges.add(BadgeType.THIRTY_MINUTES_SOLVE);
        }

        // 60분 배지 부여
        if (beforeQuizSolvingDuration < 60 && afterQuizSolvingDuration >= 60) {
            grantedBadges.add(BadgeType.ONE_HOUR_SOLVE);
        }

        this.quizSolvingDuration = afterQuizSolvingDuration;
        return grantedBadges;
    }

    // 로그인 날짜 최신화 및 배지 부여
    public BadgeType updateLoginStatus() {
        if (!Objects.equals(lastLoginDate, LocalDate.now())) {
            loginDate += 1;
            lastLoginDate = LocalDate.now();
        }

        if (loginDate == 7) {
            return BadgeType.SEVEN_DAYS_LOGIN;
        }
        if (loginDate == 30) {
            return BadgeType.THIRTY_DAYS_LOGIN;
        }

        return null;
    }

    // Update
    public void updateLastLoginDate(LocalDate loginDate) {
        this.lastLoginDate = loginDate;
    }

    public void updateLoginDate(int loginDate) {
        this.loginDate = loginDate;
    }
}
