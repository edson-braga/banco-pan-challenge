package br.com.pan.changeaddress.domain.exceptions.handlers;

import br.com.pan.changeaddress.adapters.exceptions.*;
import br.com.pan.changeaddress.domain.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(AddressNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleAddressNotFoundException(AddressNotFoundException ex) {
        logger.error("Error fetching data for zipCode = {}", ex.getMessage());
        return new ErrorResponse(ex.getErrorCode(), ex.getMessage());
    }

    @ExceptionHandler(ClientNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleClientNotFoundException(ClientNotFoundException ex) {
        logger.error("Error fetching data for client = {}", ex.getMessage());
        return new ErrorResponse(ex.getErrorCode(), ex.getMessage());
    }

    @ExceptionHandler(MunicipalitiesNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleMunicipalitiesNotFoundException(MunicipalitiesNotFoundException ex) {
        logger.error("Error fetching data for state id = {}", ex.getMessage());
        return new ErrorResponse(ex.getErrorCode(), ex.getMessage());
    }

    @ExceptionHandler(AddressConversionException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleAddressConversionException(AddressConversionException ex) {
        logger.error("Error on converting data = {}", ex.getMessage());
        return new ErrorResponse(ex.getErrorCode(), ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(BadRequestException ex) {
        logger.error("Bad request = {}", ex.getMessage());
        return new ErrorResponse(ex.getErrorCode(), ex.getMessage());
    }

    @ExceptionHandler(ExternalApiException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleExternalApiException(ExternalApiException ex) {
        logger.error("External API exception = {}", ex.getMessage());
        return new ErrorResponse(ex.getErrorCode(), ex.getMessage());
    }
}
