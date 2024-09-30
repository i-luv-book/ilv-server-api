package hanium.server.i_luv_book.domain.user.application.dto;

import hanium.server.i_luv_book.domain.user.application.dto.request.ChildCreateCommand;
import hanium.server.i_luv_book.domain.user.domain.Badge;
import hanium.server.i_luv_book.domain.user.domain.Child;
import hanium.server.i_luv_book.domain.user.domain.Child.ChildBuilder;
import hanium.server.i_luv_book.domain.user.domain.ChildBadge;
import hanium.server.i_luv_book.domain.user.domain.ChildBadge.ChildBadgeBuilder;
import hanium.server.i_luv_book.domain.user.domain.Parent;
import hanium.server.i_luv_book.domain.user.domain.notification.NotificationInfo;
import hanium.server.i_luv_book.domain.user.domain.notification.NotificationInfo.NotificationInfoBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-24T15:32:12+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.11 (Amazon.com Inc.)"
)
@Component
public class UserCommandMapperImpl implements UserCommandMapper {

    @Override
    public Child toChild(ChildCreateCommand childCreateCommand, Parent parent) {
        if ( childCreateCommand == null && parent == null ) {
            return null;
        }

        ChildBuilder child = Child.builder();

        if ( childCreateCommand != null ) {
            child.childCreateCommand( childCreateCommand );
        }
        if ( parent != null ) {
            child.parent( parent );
        }

        return child.build();
    }

    @Override
    public ChildBadge toChildBadge(Child child, Badge badge) {
        if ( child == null && badge == null ) {
            return null;
        }

        ChildBadgeBuilder childBadge = ChildBadge.builder();

        if ( child != null ) {
            childBadge.child( child );
        }
        if ( badge != null ) {
            childBadge.badge( badge );
        }

        return childBadge.build();
    }

    @Override
    public NotificationInfo toNotificationInfo(Long childId, String fcmToken) {
        if ( childId == null && fcmToken == null ) {
            return null;
        }

        NotificationInfoBuilder notificationInfo = NotificationInfo.builder();

        if ( childId != null ) {
            notificationInfo.childId( childId );
        }
        if ( fcmToken != null ) {
            notificationInfo.fcmToken( fcmToken );
        }

        return notificationInfo.build();
    }
}
