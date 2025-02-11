package com.onlinestore.backend.Exceptions;

public class JwtGenerateTokenExceptions extends Exception {

    public JwtGenerateTokenExceptions() {}

    public JwtGenerateTokenExceptions(String message) {
        super(message);
    }

    public JwtGenerateTokenExceptions(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtGenerateTokenExceptions(Throwable cause) {
        super(cause);
    }

    public JwtGenerateTokenExceptions(
        String message,
        Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace
    ) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
