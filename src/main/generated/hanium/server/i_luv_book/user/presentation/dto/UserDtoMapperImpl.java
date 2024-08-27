package hanium.server.i_luv_book.user.presentation.dto;

import hanium.server.i_luv_book.user.application.dto.request.ChildCreateCommand;
import hanium.server.i_luv_book.user.domain.Child.Gender;
import hanium.server.i_luv_book.user.presentation.dto.request.ChildCreateDto;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-27T06:18:42+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.11 (Amazon.com Inc.)"
)
@Component
public class UserDtoMapperImpl implements UserDtoMapper {

    @Override
    public ChildCreateCommand toCommand(ChildCreateDto dto, long parentId) {
        if ( dto == null ) {
            return null;
        }

        String nickname = null;
        LocalDate birthDate = null;
        Gender gender = null;
        String imgUrl = null;
        if ( dto != null ) {
            nickname = dto.getNickname();
            birthDate = dto.getBirthDate();
            gender = dto.getGender();
            imgUrl = dto.getImgUrl();
        }
        long parentId1 = 0L;
        parentId1 = parentId;

        ChildCreateCommand childCreateCommand = new ChildCreateCommand( nickname, birthDate, gender, imgUrl, parentId1 );

        return childCreateCommand;
    }
}
