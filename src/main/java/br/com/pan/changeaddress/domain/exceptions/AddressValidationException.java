package br.com.pan.changeaddress.domain.exceptions;

public class AddressValidationException extends RuntimeException {

    public AddressValidationException(String message) {
        super(message);
    }

    public AddressValidationException(String message, Throwable cause) {
        super(message, cause);
    }

}
