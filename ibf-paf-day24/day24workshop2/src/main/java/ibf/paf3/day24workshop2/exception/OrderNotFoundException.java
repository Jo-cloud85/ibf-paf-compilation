package ibf.paf3.day24workshop2.exception;

public class OrderNotFoundException extends Exception {
    
    public OrderNotFoundException (){};

    public OrderNotFoundException(String message) {
        super(message);
    }
}
