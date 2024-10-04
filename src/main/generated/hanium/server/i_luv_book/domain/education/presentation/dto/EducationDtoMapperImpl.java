package hanium.server.i_luv_book.domain.education.presentation.dto;

import hanium.server.i_luv_book.domain.education.application.dto.request.EducationContentsCreateCommand;
import hanium.server.i_luv_book.domain.education.presentation.dto.request.EducationContentsCreateDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-03T16:39:07+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.11 (Amazon.com Inc.)"
)
@Component
public class EducationDtoMapperImpl implements EducationDtoMapper {

    @Override
    public EducationContentsCreateCommand toCommand(EducationContentsCreateDto dto) {
        if ( dto == null ) {
            return null;
        }

        String title = null;
        String content = null;

        title = dto.getTitle();
        content = dto.getContent();

        String level = dto.getLevel().getValue();

        EducationContentsCreateCommand educationContentsCreateCommand = new EducationContentsCreateCommand( level, title, content );

        return educationContentsCreateCommand;
    }
}
