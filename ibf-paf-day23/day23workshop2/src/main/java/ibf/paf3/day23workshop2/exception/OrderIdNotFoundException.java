package ibf.paf3.day23workshop2.exception;

public class OrderIdNotFoundException extends RuntimeException{
    public OrderIdNotFoundException() {
        super();
    }

    public OrderIdNotFoundException(String msg){
        super(msg);
    }
}
