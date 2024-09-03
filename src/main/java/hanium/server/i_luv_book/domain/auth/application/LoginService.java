package hanium.server.i_luv_book.domain.auth.application;

import hanium.server.i_luv_book.domain.auth.dto.response.*;
import hanium.server.i_luv_book.domain.user.domain.Parent;
import hanium.server.i_luv_book.domain.user.domain.Role;
import hanium.server.i_luv_book.domain.user.domain.UserRepository;
import hanium.server.i_luv_book.domain.auth.config.LoginWebConfig;
import hanium.server.i_luv_book.domain.auth.domain.LoginType;
import hanium.server.i_luv_book.domain.auth.util.LoginWebClientUtil;
import hanium.server.i_luv_book.global.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final LoginWebClientUtil webClientUtil;
    private final LoginWebConfig loginWebConfig;
    public LoginFormResDTO getLoginFormUrl(LoginType type) {
        return new LoginFormResDTO(type.getLoginFormUrl());
    }
    public JwtTokenResponse generateJwtTokens(String code, LoginType type) {
        MultiValueMap<String, String> map = getMultiValueMap(code,type);
        UserInfoDTO userInfo = getUserInfo(map,type);
        Optional<Parent> optionalParent = userRepository.findParentBySocialIdAndLoginType(userInfo.getSocialId(), type);

        if(optionalParent.isPresent()) {
            Parent parent = optionalParent.get();
            return generateJwtToken(parent);
        } else {
            //refator 필요
            Parent parent = new Parent(userInfo.getEmail(), Parent.MembershipType.FREE,Role.ROLE_FREE,type, userInfo.getSocialId());
            userRepository.save(parent);
            return generateJwtToken(parent);
        }
    }

    private MultiValueMap<String, String> getMultiValueMap(String code,LoginType type) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        //구글과 카카오의 공통 body
        map.add("grant_type", "authorization_code");
        map.add("code", code);

        switch (type) {
            case GOOGLE:
                map.add("client_id", loginWebConfig.getGoogleClientId());
                map.add("redirect_uri", loginWebConfig.getGoogleRedirectUrl());
                map.add("client_secret", loginWebConfig.getGoogleClientSecret());
                break;
            case KAKAO:
                map.add("client_id", loginWebConfig.getKakaoRestKey());
                map.add("redirect_uri", loginWebConfig.getKakaoRedirectUrl());
                map.add("client_secret", loginWebConfig.getKakaoClientSecret());
        }
        return map;
    }
    private JwtTokenResponse generateJwtToken(Parent parent) {
        String accessToken = jwtUtil.generateAccessToken(parent.getId(), parent.getRole());
        String refreshToken = jwtUtil.generateRefreshToken(parent.getId());
        return new JwtTokenResponse(accessToken,refreshToken);
    }
    private UserInfoDTO getUserInfo(MultiValueMap<String, String> map, LoginType type) {

        switch (type) {
            case GOOGLE:
                GoogleAccessTokenDTO googleAccessToken = webClientUtil.getAceesTokenFromGoogle(map).block();
                GoogleUserInfoDTO googleUserInfo = webClientUtil.getUserInfoFromGoogle(googleAccessToken.getAccess_token()).block();
                log.info("{}",googleUserInfo);
                return new UserInfoDTO(googleUserInfo.getId(), googleUserInfo.getEmail());

            //KAKAO
            default:
                KakaoAccessTokenDTO kakaoAccessToken = webClientUtil.getAceessTokenFromKakao(map).block();
                KakaoUserInfoDTO kakaoUserInfo = webClientUtil.getUserInfoFromKakao(kakaoAccessToken.getAccess_token()).block();
                return new UserInfoDTO(kakaoUserInfo.getSub(), kakaoUserInfo.getEmail());
        }

    }
}

