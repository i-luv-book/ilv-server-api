package hanium.server.i_luv_book.domain.user.login.exception;

public class KakaoOauthException extends RuntimeException{
    public KakaoOauthException(String message) {
        super(message);
    }
}
