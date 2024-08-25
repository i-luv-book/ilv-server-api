package hanium.server.i_luv_book.user.application.dto;

import hanium.server.i_luv_book.user.application.dto.request.ChildCreateCommand;
import hanium.server.i_luv_book.user.application.dto.request.ParentCreateCommand;
import hanium.server.i_luv_book.user.domain.Child;
import hanium.server.i_luv_book.user.domain.Parent;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author ijin
 */
@Mapper(componentModel = "spring")
public interface UserCommandMapper {
    UserCommandMapper INSTANCE = Mappers.getMapper(UserCommandMapper.class);

    Child toChild(ChildCreateCommand childCreateCommand, Parent parent);

    Parent toParent(ParentCreateCommand parentCreateCommand);
}
