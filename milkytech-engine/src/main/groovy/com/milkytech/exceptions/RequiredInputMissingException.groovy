package com.milkytech.exceptions

class RequiredInputMissingException extends RuntimeException {

    public RequiredInputMissingException() {
        super();
    }

    public RequiredInputMissingException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequiredInputMissingException(String message) {
        super(message);
    }

    public RequiredInputMissingException(Throwable cause) {
        super(cause);
    }

    
}
