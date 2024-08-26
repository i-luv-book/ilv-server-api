package hanium.server.i_luv_book.security.authorize.mapper;

import hanium.server.i_luv_book.config.SecurityConfig;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 * @author Young9
 */
public class AuthorizeUrlRoleMapper{
    private final LinkedHashMap<String, String> urlRoleMappings = new LinkedHashMap<>();

    public Map<String,String> getUrlRoleMapper() {

        for (String s : SecurityConfig.ADMIN_LIST) {
            urlRoleMappings.put(s,"ROLE_ADMIN");
        }
        for (String s : SecurityConfig.WHITE_LIST) {
            urlRoleMappings.put(s,"permitAll");
        }
        for (String s : SecurityConfig.PAID_LIST) {
            urlRoleMappings.put(s,"ROLE_PAID");
        }
        for (String s : SecurityConfig.FREE_LIST) {
            urlRoleMappings.put(s,"ROLE_FREE");
        }

        return new HashMap<>(urlRoleMappings);
    }

}
