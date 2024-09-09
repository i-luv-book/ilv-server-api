package hanium.server.i_luv_book.global.security.authentication.userdetails;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
/**
 * @author Young9
 */
@AllArgsConstructor
public class JwtUserDetails implements UserDetails {

    private final Long userId;
    private final List<GrantedAuthority> roles;
    private final String uuid;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    public Long getUserId() {
        return this.userId;
    }

    public String getUuid() { return this.uuid;}

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
