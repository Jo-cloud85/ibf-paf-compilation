package ibf.paf3.day22workshop.exception;

public class RSVPNotFoundException extends RuntimeException {
    public RSVPNotFoundException() {
        super();
    }

    public RSVPNotFoundException(String msg){
        super(msg);
    }
}