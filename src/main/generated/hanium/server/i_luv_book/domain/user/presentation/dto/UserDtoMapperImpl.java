package hanium.server.i_luv_book.domain.user.presentation.dto;

import hanium.server.i_luv_book.domain.user.application.dto.request.ChildCreateCommand;
import hanium.server.i_luv_book.domain.user.application.dto.request.ParentCreateCommand;
import hanium.server.i_luv_book.domain.user.domain.Child.Gender;
import hanium.server.i_luv_book.domain.user.presentation.dto.request.ChildCreateDto;
import hanium.server.i_luv_book.domain.user.presentation.dto.request.ParentCreateDto;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-29T23:32:11+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.11 (Amazon.com Inc.)"
)
@Component
public class UserDtoMapperImpl implements UserDtoMapper {

    @Override
    public ParentCreateCommand toCommand(ParentCreateDto dto) {
        if ( dto == null ) {
            return null;
        }

        String email = null;
        String password = null;

        email = dto.getEmail();
        password = dto.getPassword();

        ParentCreateCommand parentCreateCommand = new ParentCreateCommand( email, password );

        return parentCreateCommand;
    }

    @Override
    public ChildCreateCommand toCommand(ChildCreateDto dto, long parentId) {
        if ( dto == null ) {
            return null;
        }

        String nickname = null;
        LocalDate birthDate = null;
        Gender gender = null;
        if ( dto != null ) {
            nickname = dto.getNickname();
            birthDate = dto.getBirthDate();
            gender = dto.getGender();
        }
        long parentId1 = 0L;
        parentId1 = parentId;

        ChildCreateCommand childCreateCommand = new ChildCreateCommand( nickname, birthDate, gender, parentId1 );

        return childCreateCommand;
    }
}
