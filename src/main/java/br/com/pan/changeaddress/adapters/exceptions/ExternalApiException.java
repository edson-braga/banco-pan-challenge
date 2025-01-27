package br.com.pan.changeaddress.adapters.exceptions;

public class ExternalApiException extends RuntimeException {
    private final String errorCode;

    public ExternalApiException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
