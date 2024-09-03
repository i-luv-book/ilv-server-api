package hanium.server.i_luv_book.domain.user.domain;

import hanium.server.i_luv_book.domain.auth.domain.LoginType;

import java.util.Optional;

/**
 * @author ijin
 */
public interface UserRepository {

    Optional<Parent> findParentById(long parentId);

    Optional<Parent> findParentBySocialIdAndLoginType(String socialId, LoginType loginType);

    Optional<Child> findChildById(long childId);

    Optional<Badge> findBadgeById(long badgeId);

    int countChildrenByParentId(long parentId);

    Long save(Child child);

    Long save(Parent parent);

    Long save(ChildBadge childBadge);

    void deleteChild(long parentId, String nickname);

}
