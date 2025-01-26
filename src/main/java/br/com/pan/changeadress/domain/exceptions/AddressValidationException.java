package br.com.pan.changeadress.domain.exceptions;

public class AddressValidationException extends RuntimeException {

    public AddressValidationException(String message) {
        super(message);
    }

    public AddressValidationException(String message, Throwable cause) {
        super(message, cause);
    }

}
