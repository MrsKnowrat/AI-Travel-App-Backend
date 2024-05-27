package com.example.travelactivityapp.exception;

import com.example.travelactivityapp.dto.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice // AOP concept: intercepts controllers, reaches through errors and makes them better
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class) // Handles all exceptions of this type
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleMethodArgumentValidException(MethodArgumentNotValidException exception) {
        StringBuilder errorMessages = new StringBuilder();

        List<ObjectError> result = exception.getBindingResult().getAllErrors(); // will return an array list
        for (ObjectError o : result) { // for each
            String currentErrorMessage = o.getDefaultMessage();
            errorMessages.append(currentErrorMessage).append(", ");
        }
        CommonResponse errorResponse = CommonResponse.builder().hasError(true).error(String.valueOf(errorMessages)).message(String.valueOf(errorMessages)).status(HttpStatus.BAD_REQUEST).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleRuntimeException(RuntimeException exception) {
        CommonResponse errorResponse = CommonResponse.builder().hasError(true).error(exception.getMessage()).message(exception.getMessage()).status(HttpStatus.BAD_REQUEST).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleException(Exception exception) {
        CommonResponse errorResponse = CommonResponse.builder().hasError(true).error("Something went wrong").message("Something went wrong").status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
