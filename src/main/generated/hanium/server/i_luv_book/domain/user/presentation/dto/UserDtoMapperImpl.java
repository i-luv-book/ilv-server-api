package hanium.server.i_luv_book.domain.user.presentation.dto;

import hanium.server.i_luv_book.domain.user.application.dto.request.ActivityInfoCreateCommand;
import hanium.server.i_luv_book.domain.user.application.dto.request.ChildCreateCommand;
import hanium.server.i_luv_book.domain.user.application.dto.request.NotificationInfoCreateCommand;
import hanium.server.i_luv_book.domain.user.domain.Child.Gender;
import hanium.server.i_luv_book.domain.user.presentation.dto.request.ChildActivityDto;
import hanium.server.i_luv_book.domain.user.presentation.dto.request.ChildCreateDto;
import hanium.server.i_luv_book.domain.user.presentation.dto.request.NotificationInfoDto;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-21T14:35:08+0900",
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

    @Override
    public ActivityInfoCreateCommand toCommand(ChildActivityDto dto) {
        if ( dto == null ) {
            return null;
        }

        String nickname = null;
        int minute = 0;

        nickname = dto.getNickname();
        minute = dto.getMinute();

        ActivityInfoCreateCommand activityInfoCreateCommand = new ActivityInfoCreateCommand( nickname, minute );

        return activityInfoCreateCommand;
    }

    @Override
    public NotificationInfoCreateCommand toCommand(NotificationInfoDto dto) {
        if ( dto == null ) {
            return null;
        }

        String nickname = null;
        String fcmToken = null;

        nickname = dto.getNickname();
        fcmToken = dto.getFcmToken();

        NotificationInfoCreateCommand notificationInfoCreateCommand = new NotificationInfoCreateCommand( nickname, fcmToken );

        return notificationInfoCreateCommand;
    }
}
