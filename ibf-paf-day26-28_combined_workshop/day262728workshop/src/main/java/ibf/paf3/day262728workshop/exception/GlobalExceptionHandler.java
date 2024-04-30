package ibf.paf3.day262728workshop.exception;

import org.slf4j.LoggerFactory;

import java.util.Date;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // You can use either way - depends on what you want to return when there is an error

    @ExceptionHandler({NoMatchFoundException.class})
    public ResponseEntity<ErrorMessage> handleInvalidException(Exception ex, HttpServletRequest request) {

        // new ErrorMessage (statusCode, timeStamp, message, description)
        ErrorMessage errMsg = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(), 
                request.getRequestURL().toString());

        log.error(ex.getMessage());

        return new ResponseEntity<ErrorMessage>(errMsg, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({IdNotFoundException.class})
    public ResponseEntity<ErrorMessage> handleInvalidException2(Exception ex, HttpServletRequest request) {

        // new ErrorMessage (statusCode, timeStamp, message, description)
        ErrorMessage errMsg = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(), 
                request.getRequestURL().toString());

        log.error(ex.getMessage());

        return new ResponseEntity<ErrorMessage>(errMsg, HttpStatus.NOT_FOUND);
    }
}
