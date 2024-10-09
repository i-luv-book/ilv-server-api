package hanium.server.i_luv_book.domain.user.domain.notification;

public interface BadgeNotificationListener {
    void notifyBadgeGranted(BadgeGrantedEvent event);
}
