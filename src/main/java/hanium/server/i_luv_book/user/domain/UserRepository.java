package hanium.server.i_luv_book.user.domain;

import java.util.Optional;

/**
 * @author ijin
 */
public interface UserRepository {

    Optional<Parent> findParentById(long parentId);

    int countChildrenByParentId(long parentId);

    void save(Child child);

    void save(Parent parent);
}
