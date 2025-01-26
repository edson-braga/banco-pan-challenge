package br.com.pan.changeadress.domain.exceptions.handlers;

import br.com.pan.changeadress.domain.ErrorResponse;
import br.com.pan.changeadress.domain.exceptions.AddressNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AddressNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleAddressNotFoundException(AddressNotFoundException ex) {
        return new ErrorResponse(ex.getErrorCode(), ex.getMessage());
    }
}
