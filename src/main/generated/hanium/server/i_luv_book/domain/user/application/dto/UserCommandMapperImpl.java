package hanium.server.i_luv_book.domain.user.application.dto;

import hanium.server.i_luv_book.domain.user.application.dto.request.ChildCreateCommand;
import hanium.server.i_luv_book.domain.user.application.dto.request.ParentCreateCommand;
import hanium.server.i_luv_book.domain.user.domain.Badge;
import hanium.server.i_luv_book.domain.user.domain.Child;
import hanium.server.i_luv_book.domain.user.domain.Child.ChildBuilder;
import hanium.server.i_luv_book.domain.user.domain.ChildBadge;
import hanium.server.i_luv_book.domain.user.domain.ChildBadge.ChildBadgeBuilder;
import hanium.server.i_luv_book.domain.user.domain.Parent;
import hanium.server.i_luv_book.domain.user.domain.Parent.ParentBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-03T08:31:14+0900",
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
    public Parent toParent(ParentCreateCommand parentCreateCommand) {
        if ( parentCreateCommand == null ) {
            return null;
        }

        ParentBuilder parent = Parent.builder();

        parent.parentCreateCommand( parentCreateCommand );

        return parent.build();
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
}
