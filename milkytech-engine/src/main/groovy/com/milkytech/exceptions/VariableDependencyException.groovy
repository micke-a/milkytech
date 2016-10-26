package com.milkytech.exceptions

class VariableDependencyException extends RuntimeException {

    public VariableDependencyException() {
        super();
    }

    public VariableDependencyException(String message, Throwable cause) {
        super(message, cause);
    }

    public VariableDependencyException(String message) {
        super(message);
    }

    public VariableDependencyException(Throwable cause) {
        super(cause);
    }

}
