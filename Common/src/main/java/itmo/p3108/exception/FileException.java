package itmo.p3108.exception;

/**
 * FileExceptions provide messages when xml fail has wrong data
 */
public class FileException extends RuntimeException {

    public FileException(String message) {
        super(message);
    }

}
