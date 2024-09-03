package hanium.server.i_luv_book.domain.user.domain;

import java.util.Optional;

/**
 * @author ijin
 */
public interface UserRepository {

    Optional<Parent> findParentById(long parentId);
    Optional<Child> findChildById(long childId);

    int countChildrenByParentId(long parentId);

    Long save(Child child);

    Long save(Parent parent);

    void deleteChild(long parentId, String nickname);

}
