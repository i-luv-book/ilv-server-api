package hanium.server.i_luv_book.domain.user.login.application;

import hanium.server.i_luv_book.domain.user.domain.Parent;
import hanium.server.i_luv_book.domain.user.domain.Role;
import hanium.server.i_luv_book.domain.user.infra.UserDataJpaRepository;
import hanium.server.i_luv_book.domain.user.login.config.LoginWebConfig;
import hanium.server.i_luv_book.domain.user.login.domain.LoginType;
import hanium.server.i_luv_book.domain.user.login.dto.response.JwtTokenResponse;
import hanium.server.i_luv_book.domain.user.login.dto.response.KakaoAccessTokenDTO;
import hanium.server.i_luv_book.domain.user.login.dto.response.KakaoUserInfoDTO;
import hanium.server.i_luv_book.domain.user.login.dto.response.LoginFormResDTO;
import hanium.server.i_luv_book.domain.user.login.util.LoginWebClientUtil;
import hanium.server.i_luv_book.global.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {

    private final JwtUtil jwtUtil;
    private final UserDataJpaRepository userDataJpaRepository;
    private final LoginWebClientUtil webClientUtil;
    private final LoginWebConfig loginWebConfig;
    public LoginFormResDTO getLoginFormUrl(LoginType type) {
        return new LoginFormResDTO(type.getLoginFormUrl());
    }

    @Transactional
    public JwtTokenResponse generateJWTForKaKao(String code) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("client_id", loginWebConfig.getKakaoRestKey());
        map.add("redirect_uri", loginWebConfig.getKakaoRedirectUrl());
        map.add("code", code);
        map.add("client_secret", loginWebConfig.getKakaoClentSecret());

        KakaoAccessTokenDTO kakaoAccessToken = webClientUtil.setMonoForKakao(map)
                .doOnError(error -> log.error("Error while fetching Kakao Access Token", error)).block();
        KakaoUserInfoDTO kakaoUserInfo = webClientUtil.getUserInfoFromKakao(kakaoAccessToken.getAccess_token()).block();
        Optional<Parent> optionalParent = userDataJpaRepository.findBySocialIdAndLoginType(kakaoUserInfo.getSub(),LoginType.KAKAO);

        if(optionalParent.isPresent()) {
            Parent parent = optionalParent.get();
            String accessToken = jwtUtil.generateAccessToken(parent.getId(), parent.getRole());
            String refreshToken = jwtUtil.generateRefreshToken(parent.getId());
            return new JwtTokenResponse(accessToken, refreshToken);
        } else {
            Parent parent = Parent.builder()
                    .socialId(kakaoUserInfo.getSub())
                    .loginType(LoginType.KAKAO)
                    .email(kakaoUserInfo.getEmail())
                    .role(Role.ROLE_FREE)
                    .membershipType(Parent.MembershipType.FREE)
                    .build();

            Parent savedParent = userDataJpaRepository.save(parent);
            String accessToken = jwtUtil.generateAccessToken(savedParent.getId(), parent.getRole());
            String refreshToken = jwtUtil.generateRefreshToken(savedParent.getId());
            return new JwtTokenResponse(accessToken, refreshToken);
        }

    }

    public void google(String code) {
        log.info("구글 서비스");
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", loginWebConfig.getGoogleClientId());
        map.add("client_secret", loginWebConfig.getGoogleClientSecret());
        map.add("code", code);
        map.add("grant_type", "authorization_code");
        map.add("redirect_uri", loginWebConfig.getGoogleRedirectUrl());


        webClientUtil.setMonoForGoogle(map);

    }
}

