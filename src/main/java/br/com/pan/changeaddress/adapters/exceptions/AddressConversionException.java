package br.com.pan.changeaddress.adapters.exceptions;

public class AddressConversionException extends RuntimeException {

    private final String errorCode;

    public AddressConversionException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
