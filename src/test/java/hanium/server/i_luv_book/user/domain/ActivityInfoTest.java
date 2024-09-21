package hanium.server.i_luv_book.user.domain;

import hanium.server.i_luv_book.domain.user.domain.ActivityInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static hanium.server.i_luv_book.domain.user.domain.Badge.*;

class ActivityInfoTest {

    @Test
    @DisplayName("활동내역 정보 업데이트 테스트")
    public void updateActivityInfo() {
        // given
        ActivityInfo activityInfo = ActivityInfo.builder().build();

        // when
        activityInfo.updateFairytaleReadingDuration(63);
        List<BadgeType> badgeTypes = activityInfo.updateFairytaleReadingDuration(33);

        // then
        Assertions.assertEquals(badgeTypes.size(), 0);
    }

    @Test
    @DisplayName("로그인 정보 업데이트 테스트")
    public void updateLoginInfo() {
        // given
        ActivityInfo activityInfo = ActivityInfo.builder().build();

        // when
        activityInfo.updateLoginDate(6);
        activityInfo.updateLastLoginDate(LocalDate.now().minusDays(1));
        BadgeType badgeType = activityInfo.updateLoginStatus();

        // then
        Assertions.assertEquals(badgeType, BadgeType.SEVEN_DAYS_LOGIN);
    }
}
