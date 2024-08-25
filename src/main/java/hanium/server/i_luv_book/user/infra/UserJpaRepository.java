package hanium.server.i_luv_book.user.infra;

import hanium.server.i_luv_book.user.domain.Child;
import hanium.server.i_luv_book.user.domain.Parent;
import hanium.server.i_luv_book.user.domain.UserRepository;
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
    public int countChildrenByParentId(long parentId) {
        return em.createQuery("select count(c) from Child c where c.parent.id = :parentId", Long.class)
                .setParameter("parentId", parentId)
                .getSingleResult()
                .intValue();
    }

    @Override
    public void save(Child child) {
        em.persist(child);
    }

    @Override
    public void save(Parent parent) {
        em.persist(parent);
    }
}
