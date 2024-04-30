package ibf.paf3.day262728workshop.exception;

public class IdNotFoundException extends RuntimeException {
    public IdNotFoundException(){}

    public IdNotFoundException(String msg) {
        super(msg);
    }
}
