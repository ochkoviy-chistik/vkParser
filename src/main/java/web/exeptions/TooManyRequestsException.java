package web.exeptions;

public class TooManyRequestsException
        extends Exception {
    public TooManyRequestsException(String msg) {
        super(msg);
    }
}
