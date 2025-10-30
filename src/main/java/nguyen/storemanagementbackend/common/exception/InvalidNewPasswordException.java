package nguyen.storemanagementbackend.common.exception;

public class InvalidNewPasswordException extends RuntimeException {
    public InvalidNewPasswordException(String message) {
        super(message);
    }
}
