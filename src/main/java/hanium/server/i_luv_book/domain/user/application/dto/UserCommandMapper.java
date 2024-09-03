package hanium.server.i_luv_book.domain.user.application.dto;

import hanium.server.i_luv_book.domain.user.application.dto.request.ChildCreateCommand;
import hanium.server.i_luv_book.domain.user.domain.Badge;
import hanium.server.i_luv_book.domain.user.domain.Child;
import hanium.server.i_luv_book.domain.user.domain.ChildBadge;
import hanium.server.i_luv_book.domain.user.domain.Parent;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author ijin
 */
@Mapper(componentModel = "spring")
public interface UserCommandMapper {
    UserCommandMapper INSTANCE = Mappers.getMapper(UserCommandMapper.class);

    Child toChild(ChildCreateCommand childCreateCommand, Parent parent);

    ChildBadge toChildBadge(Child child, Badge badge);
}
