package kwiz.backend.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
    
    @ExceptionHandler
    public ResponseEntity<ExceptionInfo> handleException(ResourceNotFoundException exception) {

        ExceptionInfo exceptionInfo = new ExceptionInfo(exception.getMessage(), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<ExceptionInfo>(exceptionInfo, HttpStatus.BAD_REQUEST);

    }

}
