package hanium.server.i_luv_book.domain.user.presentation.dto;

import hanium.server.i_luv_book.domain.user.application.dto.request.ActivityInfoCreateCommand;
import hanium.server.i_luv_book.domain.user.application.dto.request.ChildCreateCommand;
import hanium.server.i_luv_book.domain.user.application.dto.request.NotificationInfoCreateCommand;
import hanium.server.i_luv_book.domain.user.presentation.dto.request.ChildActivityDto;
import hanium.server.i_luv_book.domain.user.presentation.dto.request.ChildCreateDto;
import hanium.server.i_luv_book.domain.user.presentation.dto.request.NotificationInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author ijin
 */
@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    UserDtoMapper INSTANCE = Mappers.getMapper(UserDtoMapper.class);

    ChildCreateCommand toCommand(ChildCreateDto dto, long parentId);

    ActivityInfoCreateCommand toCommand(ChildActivityDto dto);

    NotificationInfoCreateCommand toCommand(NotificationInfoDto dto);
}
