package br.com.pan.changeaddress.adapters.exceptions;

public class ClientNotFoundException extends RuntimeException {

    private final String errorCode;

    public ClientNotFoundException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
