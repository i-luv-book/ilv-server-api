package hanium.server.i_luv_book.user.login.application;

import hanium.server.i_luv_book.jwt.util.JwtUtil;
import hanium.server.i_luv_book.user.login.config.LoginWebConfig;
import hanium.server.i_luv_book.user.login.domain.LoginType;
import hanium.server.i_luv_book.user.login.dto.LoginFormResDTO;
import hanium.server.i_luv_book.user.login.util.LoginWebClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final JwtUtil jwtUtil;
    private final LoginWebClientUtil webClientUtil;
    private final LoginWebConfig loginWebConfig;

    public LoginFormResDTO getLoginFormUrl(LoginType type) {
        return new LoginFormResDTO(type.getLoginFormUrl());
    }

    public void Test(String code) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type","authorization_code");
        map.add("client_id", loginWebConfig.getKakaoRestKey());
        map.add("redirect_uri", loginWebConfig.getKakaoRedirectUrl());
        map.add("code",code);
        map.add("client_secret", loginWebConfig.getKakaoClentSecret());
        //webClientUtil.requestAccessTokenAndOidcFromKakao(map);
    }
}

