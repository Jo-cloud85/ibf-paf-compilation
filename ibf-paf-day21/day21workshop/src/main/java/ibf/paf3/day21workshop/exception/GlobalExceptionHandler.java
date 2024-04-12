package ibf.paf3.day21workshop.exception;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

/* @ControllerAdvice annotation will listen to and consume all exceptions. If you exclude, then
Spring Boot will throw its own stack trace showing the errors. Basically, I am trying to
overwrite the default Spring Boot error resolver */

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // You can use either way - depends on what you want to return when there is an error

    // @ExceptionHandler(CustomerNotFoundException.class)
    // public ResponseEntity<ErrorMessage> handleException(Exception ex, HttpServletRequest request) {
    //     ErrorMessage errMsg = new ErrorMessage(
    //             HttpStatus.NOT_FOUND.value(),
    //             new Date(),
    //             ex.getMessage(), 
    //             request.getRequestURL().toString());

    //     LOGGER.error(ex.getMessage());

    //     return new ResponseEntity<ErrorMessage>(errMsg, HttpStatus.NOT_FOUND);
    // }

    @ExceptionHandler({CustomerNotFoundException.class})
    public ModelAndView handleException(Exception ex, HttpServletRequest request) {
        final ModelAndView mav  = new ModelAndView("error_page.html");
        log.error(ex.getMessage());
        mav.addObject("error_date", new Date());
        mav.addObject("error_message", ex.getMessage());
        mav.addObject("error_statusCode", HttpStatus.NOT_FOUND);
        mav.addObject("url", request.getRequestURL().toString());
        return mav;
    }
}
