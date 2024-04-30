package ibf.paf3.day262728workshop.exception;

public class NoMatchFoundException extends RuntimeException {
    public NoMatchFoundException(){}

    public NoMatchFoundException(String msg) {
        super(msg);
    }
}
