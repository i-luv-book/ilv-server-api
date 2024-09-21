package hanium.server.i_luv_book.domain.user.domain.notification;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author ijin
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class NotificationInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_info_id")
    private long id;
    @Column(name = "child_id")
    private long childId;
    @Column(name = "fcm_token")
    private String fcmToken;
    @Column(name = "is_notified")
    private boolean isNotified;

    @Builder
    public NotificationInfo(long childId, String fcmToken) {
        this.childId = childId;
        this.fcmToken = fcmToken;
        this.isNotified = true;
    }

    public boolean updateIsNotified() {
        isNotified = !isNotified;
        return isNotified;
    }
}
