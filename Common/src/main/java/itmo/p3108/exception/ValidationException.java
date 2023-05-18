package itmo.p3108.exception;

/**
 * Class ValidationException provide messages when validation of data is failed.
 * Data can be command,commands' arguments
 */
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}
