package dk.cvr.backend.exception;

public class CompanyServiceException extends RuntimeException {
    public CompanyServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}