package hanium.server.i_luv_book.domain.user.application.dto.request;

public record NotificationInfoCreateCommand(String nickname, String fcmToken) {
}
