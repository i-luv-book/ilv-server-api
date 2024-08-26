package hanium.server.i_luv_book.security.authorize.mapper;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class AuthorizeUrlRoleMapper{
    private final LinkedHashMap<String, String> urlRoleMappings = new LinkedHashMap<>();

    public Map<String,String> getUrlRoleMapper() {

        // 전체 허용
        urlRoleMappings.put("/","permitAll");
        urlRoleMappings.put("/login","permitAll");
        urlRoleMappings.put("/logout","permitAll");
        urlRoleMappings.put("/auth","permitAll");
        urlRoleMappings.put("/refresh","permitAll");


        urlRoleMappings.put("/api/badge/**","ROLE_PAID");

        // 모든 유저
        urlRoleMappings.put("/api/**","ROLE_FREE");

        // 유료 유저

        // 관리자
        urlRoleMappings.put("/api/admin/**","ROLE_ADMIN");

        return new HashMap<>(urlRoleMappings);
    }

}
