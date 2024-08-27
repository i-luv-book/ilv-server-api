package hanium.server.i_luv_book.user.domain;

import java.util.Optional;

/**
 * @author ijin
 */
public interface UserRepository {

    Optional<Parent> findParentById(long parentId);

    int countChildrenByParentId(long parentId);

    Long save(Child child);

    Long save(Parent parent);
}
