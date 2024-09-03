package hanium.server.i_luv_book.domain.auth.api;

import hanium.server.i_luv_book.domain.auth.application.LoginService;
import hanium.server.i_luv_book.domain.auth.domain.LoginType;
import hanium.server.i_luv_book.domain.auth.dto.response.JwtTokenResponse;
import hanium.server.i_luv_book.domain.auth.dto.response.LoginFormResDTO;
import hanium.server.i_luv_book.global.jwt.dao.RefreshTokenRepository;
import hanium.server.i_luv_book.global.jwt.util.JwtUtil;
import hanium.server.i_luv_book.global.security.authentication.userdetails.JwtUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginController {

    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final LoginService loginService;

    @GetMapping( "/oauth/login")
    public LoginFormResDTO OAuthFormContorller(@RequestParam("type") LoginType type) {
        return loginService.getLoginFormUrl(type);
    }
    @GetMapping("/oauth/signup")
    public JwtTokenResponse OAuthSignController(
            @RequestParam("type") LoginType type,
            @RequestParam("code") String code) {
        return loginService.generateJWTForKaKao(code,type);
    }



    @GetMapping("/api/fairytale")
    public String getTale(@AuthenticationPrincipal JwtUserDetails jwtUserDetails){
        log.info("userId {} ",jwtUserDetails.getUserId());
        return "0";
        }

    @GetMapping("/api/badge")
    public String getBadge(){
        log.info("뱃지 컨트롤러 호출");
        return "0";
        //return String.valueOf(userDetails.getUserId());
    }



}
