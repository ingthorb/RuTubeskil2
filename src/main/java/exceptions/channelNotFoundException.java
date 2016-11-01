package exceptions;

/**
 * Created by Laufey on 01/11/2016.
 */
public class channelNotFoundException extends Exception{
    public channelNotFoundException() {
    }

    public channelNotFoundException(String message) {
        super(message);
    }

    public channelNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public channelNotFoundException(Throwable cause) {
        super(cause);
    }

    public channelNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
