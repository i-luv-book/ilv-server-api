package hanium.server.i_luv_book.user.presentation.dto;

import hanium.server.i_luv_book.user.application.dto.request.ChildCreateCommand;
import hanium.server.i_luv_book.user.presentation.dto.request.ChildCreateDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author ijin
 */
@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    UserDtoMapper INSTANCE = Mappers.getMapper(UserDtoMapper.class);

    ChildCreateCommand toCommand(ChildCreateDto dto, long parentId);
}
