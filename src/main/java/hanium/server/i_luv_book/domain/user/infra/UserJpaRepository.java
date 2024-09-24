package hanium.server.i_luv_book.domain.user.infra;

import hanium.server.i_luv_book.domain.auth.domain.LoginType;
import hanium.server.i_luv_book.domain.user.domain.*;
import hanium.server.i_luv_book.domain.user.domain.notification.NotificationInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author ijin
 */
@Repository
@RequiredArgsConstructor
public class UserJpaRepository implements UserRepository {

    private final EntityManager em;

    @Override
    public Optional<Parent> findParentById(long parentId) {
        return Optional.ofNullable(em.find(Parent.class, parentId));
    }

    @Override
    public Optional<Parent> findParentBySocialIdAndLoginType(String socialId, LoginType loginType) {
        List<Parent> parent = em.createQuery(
                "select p from Parent p where p.socialId = :socialId and p.loginType = :loginType", Parent.class)
                .setParameter("socialId", socialId)
                .setParameter("loginType", loginType)
                .getResultList();
        return parent.stream().findAny();

    }

    @Override
    public Optional<Child> findChildById(long childId) {
        List<Child> children = em.createQuery("select c from Child c" +
                        " left join c.activityInfo a" +
                        " where c.id = :childId", Child.class)
                .setParameter("childId", childId)
                .getResultList();
        return children.stream().findAny();
    }

    @Override
    public Optional<Child> findChildByParentIdAndNickname(long parentId, String nickname) {
        List<Child> children = em.createQuery("select c from Child c" +
                        " left join c.activityInfo a" +
                        " where c.nickname = :nickname and c.parent.id = :parentId", Child.class)
                .setParameter("parentId", parentId)
                .setParameter("nickname", nickname)
                .getResultList();
        return children.stream().findAny();
    }

    @Override
    public void save(Badge badge) {
        em.persist(badge);
    }

    @Override
    public Optional<Badge> findBadgeByType(Badge.BadgeType badgeType) {
        List<Badge> badges = em.createQuery("select b from Badge b" +
                        " where b.type = :badgeType", Badge.class)
                .setParameter("badgeType", badgeType)
                .getResultList();
        return badges.stream().findAny();
    }

    @Override
    public int countChildrenByParentId(long parentId) {
        return em.createQuery("select count(c) from Child c where c.parent.id = :parentId", Long.class)
                .setParameter("parentId", parentId)
                .getSingleResult()
                .intValue();
    }

    @Override
    public Long save(Child child) {
        em.persist(child);
        return child.getId();
    }

    @Override
    public Long save(Parent parent) {
        em.persist(parent);
        return parent.getId();
    }

    @Override
    public Long save(ChildBadge childBadge) {
        em.persist(childBadge);
        return childBadge.getId();
    }

    @Override
    public Long save(NotificationInfo notificationInfo) {
        em.persist(notificationInfo);
        return notificationInfo.getId();
    }

    @Override
    public void deleteChild(long parentId, String nickname) {
        Child child = em.createQuery("select c from Child c "
                        + "where c.parent.id = :parentId and c.nickname = :nickname", Child.class)
                .setParameter("parentId", parentId)
                .setParameter("nickname", nickname)
                .getSingleResult();
        em.remove(child);
    }

    @Override
    public Optional<NotificationInfo> findNotificationInfoByChildId(long childId) {
        List<NotificationInfo> notificationInfos = em.createQuery("select ni from NotificationInfo ni " +
                "where ni.childId = :childId", NotificationInfo.class)
                .setParameter("childId", childId)
                .getResultList();
        return notificationInfos.stream().findAny();
    }

    @Override
    public boolean checkNotificationAgreement(String nickname) {
        List<NotificationInfo> notificationInfos = em.createQuery("select ni " +
                "from NotificationInfo ni " +
                "where ni.childId in (select c.id from Child c where c.nickname = :nickname)", NotificationInfo.class)
                .setParameter("nickname", nickname)
                .getResultList();

        if (notificationInfos.isEmpty())
            return false;
        return notificationInfos.get(0).isNotified();
    }
}
