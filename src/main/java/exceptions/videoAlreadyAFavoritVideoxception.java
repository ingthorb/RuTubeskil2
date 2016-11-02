package exceptions;

/**
 * Created by Laufey on 02/11/2016.
 */
public class videoAlreadyAFavoritVideoxception extends Exception{
    public videoAlreadyAFavoritVideoxception() {
    }

    public videoAlreadyAFavoritVideoxception(String message) {
        super(message);
    }

    public videoAlreadyAFavoritVideoxception(String message, Throwable cause) {
        super(message, cause);
    }

    public videoAlreadyAFavoritVideoxception(Throwable cause) {
        super(cause);
    }

    public videoAlreadyAFavoritVideoxception(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
