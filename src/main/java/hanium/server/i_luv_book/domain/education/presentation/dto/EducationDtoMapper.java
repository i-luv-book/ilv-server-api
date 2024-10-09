package hanium.server.i_luv_book.domain.education.presentation.dto;

import hanium.server.i_luv_book.domain.education.application.dto.request.EducationContentsCreateCommand;
import hanium.server.i_luv_book.domain.education.application.dto.request.QuizSolveCommand;
import hanium.server.i_luv_book.domain.education.presentation.dto.request.EducationContentsCreateDto;
import hanium.server.i_luv_book.domain.education.presentation.dto.request.QuizSolveDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface EducationDtoMapper {
    EducationDtoMapper INSTANCE = Mappers.getMapper(EducationDtoMapper.class);

    @Mapping(target = "level", expression = "java(dto.getLevel().getValue())")
    EducationContentsCreateCommand toCommand(EducationContentsCreateDto dto);

    @Mapping(target = "childAnswerCommands", source = "childAnswerDtos")
    QuizSolveCommand toCommand(QuizSolveDto dto);
}
