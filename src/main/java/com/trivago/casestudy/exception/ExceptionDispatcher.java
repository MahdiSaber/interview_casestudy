package com.trivago.casestudy.exception;

import com.trivago.casestudy.dto.HttpErrorResponse;
import com.trivago.casestudy.exception.domain.AccommodationNotFoundException;
import com.trivago.casestudy.exception.domain.PriceNegativeException;
import com.trivago.casestudy.exception.domain.PriceNullException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;


@RestControllerAdvice
public class ExceptionDispatcher implements ErrorController {

    // Error messages
    public static final String PRICE_NEGATIVE_ERROR = "Price cannot be negative.";
    public static final String PRICE_NULL_ERROR = "Price cannot be null.";
    private static final String PROCESSING_FILE_ERROR = "An error occurred while processing the file. Please try again.";
    private static final String INTERNAL_ERROR = "An internal server error occurred. Please try again later.";
    public static final String DOMAIN_TEST_ERROR = "/error";


    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ResponseEntity<HttpErrorResponse> buildErrorResponse(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpErrorResponse(httpStatus, message), httpStatus);
    }

    @ExceptionHandler(AccommodationNotFoundException.class)
    public ResponseEntity<HttpErrorResponse> accommodationNotFoundException(AccommodationNotFoundException exception) {
        logger.error(exception.getMessage());
        return buildErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(PriceNegativeException.class)
    public ResponseEntity<HttpErrorResponse> priceNegativeException(PriceNegativeException exception) {
        logger.error(exception.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, PRICE_NEGATIVE_ERROR);
    }

    @ExceptionHandler(PriceNullException.class)
    public ResponseEntity<HttpErrorResponse> priceNullException(PriceNullException exception) {
        logger.error(exception.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, PRICE_NULL_ERROR);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<HttpErrorResponse> iOException(IOException exception) {
        logger.error(exception.getMessage());
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, PROCESSING_FILE_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<HttpErrorResponse> exception(Exception exception) {
        logger.error(exception.getMessage());
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_ERROR);
    }

}
