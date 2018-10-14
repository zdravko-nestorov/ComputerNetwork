package bg.znestorov.exceptions;

public class NotCorrectInputParamsException extends Exception {

    public NotCorrectInputParamsException(String msg) {
        super("The input parameters are not correct: " + msg);
    }

}