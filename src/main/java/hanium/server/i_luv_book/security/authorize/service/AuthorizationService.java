package hanium.server.i_luv_book.security.authorize.service;

import hanium.server.i_luv_book.security.authorize.mapper.AuthorizeUrlRoleMapper;

import java.util.Map;
/**
 * @author Young9
 */
public class AuthorizationService {
    private final AuthorizeUrlRoleMapper delegate;

    public AuthorizationService(AuthorizeUrlRoleMapper delegate) {
        this.delegate = delegate;
    }
    public Map<String,String> getUrlRoleMappings() {
        return delegate.getUrlRoleMapper();
    }

}
