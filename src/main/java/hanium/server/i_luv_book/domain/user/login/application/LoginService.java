package hanium.server.i_luv_book.domain.user.login.application;

import hanium.server.i_luv_book.domain.user.login.config.LoginWebConfig;
import hanium.server.i_luv_book.domain.user.login.domain.LoginType;
import hanium.server.i_luv_book.domain.user.login.dto.response.LoginFormResDTO;
import hanium.server.i_luv_book.domain.user.login.util.LoginWebClientUtil;
import hanium.server.i_luv_book.global.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
@Slf4j
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

        webClientUtil.setMonoForKakao(map).subscribe(response -> webClientUtil.getUserInfoFromKakao(response.getAccess_token()));
    }

    public void google(String code) {
        log.info("구글 서비스");
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", loginWebConfig.getGoogleClientId());
        map.add("client_secret", loginWebConfig.getGoogleClientSecret());
        map.add("code",code);
        map.add("grant_type","authorization_code");
        map.add("redirect_uri", loginWebConfig.getGoogleRedirectUrl());


        webClientUtil.setMonoForGoogle(map);

    }
}

