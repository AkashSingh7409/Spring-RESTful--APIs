package com.trantor.addressbookapi.exception;

import com.trantor.addressbookapi.exception.custom.ContactNotFoundException;
import com.trantor.addressbookapi.exception.custom.EmptyDatabaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errorMap.put(error.getField(), error.getDefaultMessage()));

        return errorMap;
    }

    @ResponseStatus
    @ExceptionHandler(ContactNotFoundException.class)
    public Map<String, String> handleNotFoundException(ContactNotFoundException ex) {
        HashMap<String, String> errorMap = new HashMap<>();
        errorMap.put("FirstName", ex.getMessage());
        return errorMap;

    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ResourceAccessException.class)
    public String handleConnectionTimeout() {
        return "Connection Is not Established|please try again later";

    }

    @ExceptionHandler(EmptyDatabaseException.class)
    public String databaseEmpty() {
        return "Database is Empty";

    }
}
