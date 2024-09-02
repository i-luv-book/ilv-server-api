package hanium.server.i_luv_book.global.security.authentication.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
/**
 * @author Young9
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;
    private final Object credentails;

    public JwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities, Object principal, Object credentails) {
        //권한주입
        super(authorities);
        this.principal = principal;
        this.credentails = credentails;
        setAuthenticated(true);
    }

    public JwtAuthenticationToken(Object principal, Object credentails) {
        //권한을 null로 등록
        super(null);
        this.principal = principal;
        this.credentails = credentails;
        setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return this.credentails;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
