package dk.cvr.backend.exception;

// Project imports
import dk.cvr.backend.dto.ErrorResponseDTO;

// Spring imports
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CompanyNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleCompanyNotFound(CompanyNotFoundException e) {
        ErrorResponseDTO err = new ErrorResponseDTO(
                e.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );

        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CompanyServiceException.class)
    public ResponseEntity<ErrorResponseDTO> handleCompanyServiceException(CompanyServiceException e) {
        e.printStackTrace();

        ErrorResponseDTO err = new ErrorResponseDTO(
                "Internal server error while processing company request",
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );

        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}