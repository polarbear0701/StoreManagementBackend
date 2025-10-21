package nguyen.storemanagementbackend.common.exception;

public class NoStoreFoundException extends RuntimeException {
    public NoStoreFoundException(String message) {
        super(message);
    }
}
