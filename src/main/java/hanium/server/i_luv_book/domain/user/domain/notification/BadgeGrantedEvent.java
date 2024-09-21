package hanium.server.i_luv_book.domain.user.domain.notification;

import java.util.List;

import static hanium.server.i_luv_book.domain.user.domain.Badge.*;

public record BadgeGrantedEvent(List<BadgeType> badgeTypes, Long childId) {
}
