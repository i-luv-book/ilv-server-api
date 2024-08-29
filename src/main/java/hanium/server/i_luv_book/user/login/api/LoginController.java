package hanium.server.i_luv_book.user.login.api;

import hanium.server.i_luv_book.jwt.domain.RefreshToken;
import hanium.server.i_luv_book.jwt.dao.RefreshTokenRepository;
import hanium.server.i_luv_book.jwt.util.JwtUtil;
import hanium.server.i_luv_book.security.authentication.userdetails.JwtUserDetails;
import hanium.server.i_luv_book.user.Role;
import hanium.server.i_luv_book.user.login.application.LoginService;
import hanium.server.i_luv_book.user.login.domain.LoginType;
import hanium.server.i_luv_book.user.login.dto.LoginFormResDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginController {

    @Data
    @AllArgsConstructor
    private class TokenDTO {
        private String accessToken;
        private String refreshToken;
    }

    @GetMapping("/auth")
    public TokenDTO generateJWT() {
        String accessToken = jwtUtil.generateAccessToken(1l,Role.ROLE_PAID);
        String refreshToken = jwtUtil.generateRefreshToken(1l);
        log.info("토큰이 정상적으로 발급되었습니다.");

        return new TokenDTO(accessToken,refreshToken);
    }


    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final LoginService loginService;

    @GetMapping( "/login/oauth")
    public LoginFormResDTO OAuthFormContorller(@RequestParam("type") LoginType type) {
        return loginService.getLoginFormUrl(type);
    }
    @GetMapping("/signup/oauth/kakao")
    public String OAuthSignController(@RequestParam("code") String code) {
        loginService.Test(code);

        return code;
    }





    @GetMapping("/refreshToken")
    public TokenDTO regenerateJWT(HttpServletRequest request) {

        String jwtHeader = request.getHeader("Authorize");
        String jwtToken = jwtHeader.replace("Bearer ","");
        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByRefreshToken(jwtToken);

        Long userId = jwtUtil.getUserIdFromToken(jwtToken);
        Role userRole = Role.ROLE_FREE;

    if (optionalRefreshToken.isPresent()) {
            String accessToken = jwtUtil.generateAccessToken(userId,userRole);
            String refreshToken = jwtUtil.generateRefreshToken(userId);
            return new TokenDTO(accessToken,refreshToken);
        }

        return new TokenDTO("123","123");
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
