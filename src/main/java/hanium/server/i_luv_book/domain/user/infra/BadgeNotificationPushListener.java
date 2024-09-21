package hanium.server.i_luv_book.domain.user.infra;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import hanium.server.i_luv_book.domain.user.domain.UserRepository;
import hanium.server.i_luv_book.domain.user.domain.notification.BadgeGrantedEvent;
import hanium.server.i_luv_book.domain.user.domain.notification.BadgeNotificationListener;
import hanium.server.i_luv_book.domain.user.domain.notification.NotificationInfo;
import hanium.server.i_luv_book.global.exception.NotFoundException;
import hanium.server.i_luv_book.global.exception.NotificationException;
import hanium.server.i_luv_book.global.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

import static hanium.server.i_luv_book.domain.user.domain.Badge.*;

@Component
@RequiredArgsConstructor
public class BadgeNotificationPushListener implements BadgeNotificationListener {

    private final FirebaseMessaging firebaseMessaging;
    private final UserRepository userRepository;

    @Override
    @Async
    @EventListener
    public void notifyBadgeGranted(BadgeGrantedEvent event) {
        List<BadgeType> badgeTypes = event.badgeTypes();
        NotificationInfo notificationInfo = findNotificationInfo(event.childId());
        String fcmToken = notificationInfo.getFcmToken();

        // 알림 내용
        String grantedBadgesInfo = processBadgesInfo(badgeTypes);

        Notification notification = Notification.builder()
                .setTitle("배지 부여 알림")
                .setBody(grantedBadgesInfo)
                .build();
        Message message = Message.builder()
                .setToken(fcmToken)
                .setNotification(notification)
                .build();

        // 알림 푸쉬
        try {
            firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e) {
            throw new NotificationException(ErrorCode.NOTIFICATION_FAILED);
        }
    }

    private NotificationInfo findNotificationInfo(Long childId) {
        return userRepository.findNotificationInfoByChildId(childId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.FCM_TOKEN_NOT_FOUND));
    }

    private String processBadgesInfo(List<BadgeType> badgeTypes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (BadgeType badgeType : badgeTypes) {
            stringBuilder.append(badgeType.toString()).append(",");
        }
        return stringBuilder.toString();
    }
}
