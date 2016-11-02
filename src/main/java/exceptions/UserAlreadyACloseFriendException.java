package exceptions;

/**
 * Created by Laufey on 01/11/2016.
 */
public class UserAlreadyACloseFriendException extends Exception {
    public UserAlreadyACloseFriendException() {
    }

    public UserAlreadyACloseFriendException(String message) {
        super(message);
    }

    public UserAlreadyACloseFriendException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyACloseFriendException(Throwable cause) {
        super(cause);
    }

    public UserAlreadyACloseFriendException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
