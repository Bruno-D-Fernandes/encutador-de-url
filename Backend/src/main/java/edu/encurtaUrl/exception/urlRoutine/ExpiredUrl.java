package edu.encurtaUrl.exception.urlRoutine;

public class ExpiredUrl extends RuntimeException {
    public ExpiredUrl() {
        super("Url expirada");
    }

    public ExpiredUrl(String message) {
        super(message);
    }
}
