package nguyen.storemanagementbackend.common.exception;

import nguyen.storemanagementbackend.common.generic.GenericExceptionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoUserFoundException.class)
    public ResponseEntity<GenericExceptionResponseDto> handleNoUserFound(
            NoUserFoundException exception
    ) {
        GenericExceptionResponseDto error = new GenericExceptionResponseDto(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                System.currentTimeMillis(),
                exception.getClass().getSimpleName()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(error);
    }

    @ExceptionHandler(InvalidNewStoreException.class)
    public ResponseEntity<GenericExceptionResponseDto> handleInvalidNewStore(
            InvalidNewStoreException exception
    ) {
        GenericExceptionResponseDto error = new GenericExceptionResponseDto(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),
                System.currentTimeMillis(),
                exception.getClass().getSimpleName()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(error);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<GenericExceptionResponseDto> handleUserAlreadyExists(UserAlreadyExistsException exception) {
        GenericExceptionResponseDto error = new GenericExceptionResponseDto(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),
                System.currentTimeMillis(),
                exception.getClass().getSimpleName()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(error);
    }

    @ExceptionHandler(FailToRegisterException.class)
    public ResponseEntity<GenericExceptionResponseDto> handleFailToRegister(FailToRegisterException exception) {
        GenericExceptionResponseDto error = new GenericExceptionResponseDto(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),
                System.currentTimeMillis(),
                exception.getClass().getSimpleName()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(error);
    }

    @ExceptionHandler(InvalidNewPasswordException.class)
    public ResponseEntity<GenericExceptionResponseDto> handleFailToRegister(InvalidNewPasswordException exception) {
        GenericExceptionResponseDto error = new GenericExceptionResponseDto(
                HttpStatus.BAD_REQUEST.value(),
                exception.getMessage(),
                System.currentTimeMillis(),
                exception.getClass().getSimpleName()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(error);
    }
}
