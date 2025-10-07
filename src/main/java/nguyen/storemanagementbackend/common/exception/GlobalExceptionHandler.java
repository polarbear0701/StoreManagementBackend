package nguyen.storemanagementbackend.common.exception;

import nguyen.storemanagementbackend.common.generic.GenericExceptionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoUserFoundException.class)
    public ResponseEntity<GenericExceptionResponseDto> handleNoUserFound(NoUserFoundException exception) {
        GenericExceptionResponseDto error = new GenericExceptionResponseDto(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                System.currentTimeMillis()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND.value())
                .body(
                        error
                );
    }
}
