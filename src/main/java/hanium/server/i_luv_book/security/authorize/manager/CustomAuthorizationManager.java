package hanium.server.i_luv_book.security.authorize.manager;

import hanium.server.i_luv_book.security.authorize.mapper.AuthorizeUrlRoleMapper;
import hanium.server.i_luv_book.security.authorize.service.AuthorizationService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.log.LogMessage;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcherEntry;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
/**
 * @author Young9
 */
@Component
@RequiredArgsConstructor
public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final RoleHierarchy roleHierarchy;
    List<RequestMatcherEntry<AuthorizationManager<RequestAuthorizationContext>>> mappings;
    private static final AuthorizationDecision DENY = new AuthorizationDecision(false);
    private final HandlerMappingIntrospector handlerMappingIntrospector;

    @PostConstruct
    public void mapping() {
        AuthorizationService authorizationService = new AuthorizationService(new AuthorizeUrlRoleMapper());
        mappings = authorizationService.getUrlRoleMappings()
                .entrySet().stream()
                .map(entry -> new RequestMatcherEntry<>(
                        new MvcRequestMatcher(handlerMappingIntrospector, entry.getKey()),
                        getAuthorizationManager(entry.getValue())))
                .collect(Collectors.toList());
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {

        Iterator var3 = this.mappings.iterator();

        HttpServletRequest request=object.getRequest();

        RequestMatcherEntry mapping;
        RequestMatcher.MatchResult matchResult;
        do {
            if (!var3.hasNext()) {
                return DENY;
            }

            mapping = (RequestMatcherEntry)var3.next();
            RequestMatcher matcher = mapping.getRequestMatcher();
            matchResult = matcher.matcher(request);
        } while(!matchResult.isMatch());

        AuthorizationManager<RequestAuthorizationContext> manager = (AuthorizationManager)mapping.getEntry();


        return manager.check(authentication, new RequestAuthorizationContext(request, matchResult.getVariables()));
    }
    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        AuthorizationManager.super.verify(authentication, object);
    }

    private AuthorizationManager<RequestAuthorizationContext> getAuthorizationManager(String role) {
        if (role.startsWith("ROLE")) {
            AuthorityAuthorizationManager<RequestAuthorizationContext> authorizationManager
                    = AuthorityAuthorizationManager.hasAuthority(role);
            authorizationManager.setRoleHierarchy(roleHierarchy);

            return authorizationManager;
        } else {
            return new WebExpressionAuthorizationManager(role);
        }
    }


}
