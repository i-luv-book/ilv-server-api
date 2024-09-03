package hanium.server.i_luv_book.domain.user.infra;

import hanium.server.i_luv_book.domain.user.domain.Child;
import hanium.server.i_luv_book.domain.user.domain.Parent;
import hanium.server.i_luv_book.domain.user.domain.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
    public Optional<Child> findChildById(long childId) {
        return Optional.ofNullable(em.find(Child.class, childId));
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
    public void deleteChild(long parentId, String nickname) {
        Child child = em.createQuery("select c from Child c "
                        + "where c.parent.id = :parentId and c.nickname = :nickname", Child.class)
                .setParameter("parentId", parentId)
                .setParameter("nickname", nickname)
                .getSingleResult();
        em.remove(child);
    }
}
