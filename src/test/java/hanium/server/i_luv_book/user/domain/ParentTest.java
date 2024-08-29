package hanium.server.i_luv_book.user.domain;

import hanium.server.i_luv_book.domain.user.application.dto.request.ParentCreateCommand;
import hanium.server.i_luv_book.domain.user.domain.Parent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParentTest {

    @Test
    void updateMembershipType() {
        // given
        ParentCreateCommand parentCreateCommand = new ParentCreateCommand("부모1", "비밀번호1");
        Parent parent = Parent.builder().parentCreateCommand(parentCreateCommand).build();

        // when&then
        parent.updateMembershipType(Parent.MembershipType.PAID_BASIC);
        assertEquals(parent.getMembershipType(), Parent.MembershipType.PAID_BASIC);
    }

    @Test
    void canAddChild() {
        // given
        ParentCreateCommand parentCreateCommand = new ParentCreateCommand("부모1", "비밀번호1");
        Parent parent = Parent.builder().parentCreateCommand(parentCreateCommand).build();

        // when
        boolean result = parent.canAddChild(1);

        // then
        assertFalse(result);
    }
}
