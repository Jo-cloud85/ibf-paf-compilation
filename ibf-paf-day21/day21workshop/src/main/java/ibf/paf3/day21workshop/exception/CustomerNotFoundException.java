package ibf.paf3.day21workshop.exception;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException() {
        super();
    }

    public CustomerNotFoundException(String msg){
        super(msg);
    }
}