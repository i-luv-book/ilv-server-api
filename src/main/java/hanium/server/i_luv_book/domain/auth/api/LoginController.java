package hanium.server.i_luv_book.domain.auth.api;

import hanium.server.i_luv_book.domain.auth.application.LoginService;
import hanium.server.i_luv_book.domain.auth.domain.LoginType;
import hanium.server.i_luv_book.domain.auth.dto.response.JwtTokenResponse;
import hanium.server.i_luv_book.domain.auth.dto.response.LoginFormResDTO;
import hanium.server.i_luv_book.global.jwt.application.JwtService;
import hanium.server.i_luv_book.global.security.authentication.userdetails.JwtUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LoginController {

    private final LoginService loginService;
    private final JwtService jwtService;

    @GetMapping( "/oauth/login")
    public LoginFormResDTO OAuthFormContorller(@RequestParam("type") LoginType type) {
        return loginService.getLoginFormUrl(type);
    }
    @GetMapping("/oauth/signup")
    public JwtTokenResponse OAuthSignController(
            @RequestParam("type") LoginType type,
            @RequestParam("code") String code) {
        return loginService.signupAndGetJwtTokens(code,type);
    }

    @GetMapping("/auth/token/reissue")
    public JwtTokenResponse tokenReissueController(@AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
        return loginService.reissueJwtTokens(jwtUserDetails.getUuid());
    }

    @PostMapping("/auth/logout")
    public void destroyTokenController(@AuthenticationPrincipal JwtUserDetails jwtUserDetails) {
        jwtService.destroyRefreshToken(jwtUserDetails.getUuid());
    }


    @GetMapping("/fairytale")
    public String getTale(@AuthenticationPrincipal JwtUserDetails jwtUserDetails){
        log.info("userId {} ",jwtUserDetails.getUserId());
        return "0";
        }

    @GetMapping("/badge")
    public String getBadge(){
        log.info("뱃지 컨트롤러 호출");
        return "0";
    }



}
