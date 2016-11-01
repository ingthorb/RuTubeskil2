package exceptions;

/**
 * Created by Laufey on 01/11/2016.
 */
public class videoNotFoundException extends Exception {

    public videoNotFoundException() {
    }

    public videoNotFoundException(String message) {
        super(message);
    }

    public videoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public videoNotFoundException(Throwable cause) {
        super(cause);
    }

    public videoNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
