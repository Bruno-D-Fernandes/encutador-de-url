package edu.encurtaUrl.exception;


import edu.encurtaUrl.exception.urlRoutine.InvalidUrlException;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.security.auth.login.CredentialException;
import java.util.Map;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(InvalidUrlException.class)
    public ResponseEntity invalidUrlHandler(Exception e){
        String message = e.getMessage();
        return ResponseEntity.badRequest().body(Map.of("Error:", e));
    }

    @ExceptionHandler(CredentialException.class)
    public ResponseEntity CredentialExceptionHandler(Exception e){
        String message = e.getMessage();
        return ResponseEntity.badRequest().body(Map.of("Error:", e));
    }

}
