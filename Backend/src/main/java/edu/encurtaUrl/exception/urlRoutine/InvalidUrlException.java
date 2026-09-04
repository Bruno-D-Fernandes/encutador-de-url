package edu.encurtaUrl.exception.urlRoutine;

public class InvalidUrlException extends RuntimeException{

    public InvalidUrlException() {
        super("Url fornecida inválida.");
    }

    public InvalidUrlException(String message) {
        super(message);
    }
}
