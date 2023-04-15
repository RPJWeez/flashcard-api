package io.flashcards.fc.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.flashcards.fc.controller.representation.FcErrorResponse;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class ValidationExceptionHandler {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<FcErrorResponse> handle(ConstraintViolationException e) {
        //get first violation and add it to the message
        String msg = e.getConstraintViolations().stream().findAny().get().getMessage();
        FcErrorResponse resp = FcErrorResponse.builder().errorMessage(msg).build();
        return ResponseEntity.badRequest().body(resp);
    }
    
}
