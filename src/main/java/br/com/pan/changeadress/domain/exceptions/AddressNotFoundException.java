package br.com.pan.changeadress.domain.exceptions;

public class AddressNotFoundException extends RuntimeException {

    private final String errorCode;

    public AddressNotFoundException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
