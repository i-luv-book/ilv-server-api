package hanium.server.i_luv_book.domain.user.login.exception;

public class GoogleOauthException extends RuntimeException{
    public GoogleOauthException(String message) {
        super(message);
    }
}
