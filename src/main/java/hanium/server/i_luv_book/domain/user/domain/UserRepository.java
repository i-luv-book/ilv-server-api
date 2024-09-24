package hanium.server.i_luv_book.domain.user.domain;

import hanium.server.i_luv_book.domain.auth.domain.LoginType;
import hanium.server.i_luv_book.domain.user.domain.notification.NotificationInfo;

import java.util.Optional;

/**
 * @author ijin
 */
public interface UserRepository {

    Optional<Parent> findParentById(long parentId);

    Optional<Parent> findParentBySocialIdAndLoginType(String socialId, LoginType loginType);

    Optional<Child> findChildById(long childId);

    Optional<Child> findChildByParentIdAndNickname(long parentId, String nickname);

    void save(Badge badge);

    Optional<Badge> findBadgeByType(Badge.BadgeType badgeType);

    int countChildrenByParentId(long parentId);

    Long save(Child child);

    Long save(Parent parent);

    Long save(ChildBadge childBadge);

    Long save(NotificationInfo notificationInfo);

    void deleteChild(long parentId, String nickname);

    Optional<NotificationInfo> findNotificationInfoByChildId(long childId);

    boolean checkNotificationAgreement(String nickname);
}
