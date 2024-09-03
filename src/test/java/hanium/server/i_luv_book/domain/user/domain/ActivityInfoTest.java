package hanium.server.i_luv_book.domain.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ActivityInfoTest {

    @Test
    @DisplayName("활동내역 정보 업데이트 테스트")
    public void updateInfo() {
        // given
        ActivityInfo activityInfo = new ActivityInfo();

        // when
        activityInfo.updateLoginStatus();
        activityInfo.updateFairytaleReadingDuration(10);
        activityInfo.updateQuizSolvingDuration(7);

        // then
        assertEquals(1, activityInfo.getLoginDate());
        assertEquals(10, activityInfo.getFairytaleReadingDuration());
        assertEquals(7, activityInfo.getQuizSolvingDuration());
    }
}
