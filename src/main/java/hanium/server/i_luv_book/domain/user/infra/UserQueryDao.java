package hanium.server.i_luv_book.domain.user.infra;

import hanium.server.i_luv_book.domain.user.application.dto.response.ChildInfo;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author ijin
 */
@RequiredArgsConstructor
@Repository
public class UserQueryDao {

    private final EntityManager em;

    public List<ChildInfo> findChildInfosByParentId(Long parentId) {
        return em.createQuery(
                        "select new hanium.server.i_luv_book.domain.user.application.dto.response.ChildInfo(c.nickname, c.profileImage.imageUrl)" +
                                " from Child c" +
                                " left join c.parent p" +
                                " where p.id = :parentId", ChildInfo.class)
                .setParameter("parentId", parentId)
                .getResultList();
    }

    public Optional<ChildInfo> findChildInfosByChildNickname(String nickname) {
        List<ChildInfo> childInfos = em.createQuery(
                        "select new hanium.server.i_luv_book.domain.user.application.dto.response.ChildInfo(c.nickname, c.profileImage.imageUrl)" +
                                " from Child c" +
                                " where c.nickname = :nickname", ChildInfo.class)
                .setParameter("nickname", nickname)
                .getResultList();
        return childInfos.stream().findAny();
    }
}
