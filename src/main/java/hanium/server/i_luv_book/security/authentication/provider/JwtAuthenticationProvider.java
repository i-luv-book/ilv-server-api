package hanium.server.i_luv_book.security.authentication.provider;


import hanium.server.i_luv_book.security.authentication.token.JwtAuthenticationToken;
import hanium.server.i_luv_book.security.authentication.userdetails.JwtUserDetails;
import hanium.server.i_luv_book.user.Role;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
/**
 * @author Young9
 */
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        throw new AuthenticationServiceException("wrong authentication method");
    }

    public Authentication authenticate(Long userId, Role role) throws AuthenticationException {
        JwtUserDetails userDetails = new JwtUserDetails(userId, List.of(new SimpleGrantedAuthority(role.name())));
        return new JwtAuthenticationToken(userDetails.getAuthorities(),userDetails,null);
    }

    //JWT 토큰에 대해서만 검증 및 토큰 생성
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(JwtAuthenticationToken.class);
    }
}



