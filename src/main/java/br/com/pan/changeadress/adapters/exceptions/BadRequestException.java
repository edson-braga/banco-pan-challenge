package br.com.pan.changeadress.adapters.exceptions;

public class BadRequestException extends RuntimeException {
    private final String errorCode;

    public BadRequestException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
