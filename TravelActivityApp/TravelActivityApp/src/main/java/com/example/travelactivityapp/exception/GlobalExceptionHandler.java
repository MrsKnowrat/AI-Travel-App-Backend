package com.example.travelactivityapp.exception;

/* This class serves as a centralized error handler for the entire application. 
It handles all exceptions and errors that are thrown in the application and 
returns a consistent response. */

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
    @ResponseStatus(HttpStatus.BAD_REQUEST) // Sets the HTTP status to 400 BAD_REQUEST
    public ResponseEntity<?> handleMethodArgumentValidException(MethodArgumentNotValidException exception) { // Takes in the exception object
        StringBuilder errorMessages = new StringBuilder(); // StringBuilder for collecting error messages

        List<ObjectError> result = exception.getBindingResult().getAllErrors(); // will return an array list/all validation errors
        for (ObjectError o : result) { // for each validation error
            String currentErrorMessage = o.getDefaultMessage(); // gets the default error message
            errorMessages.append(currentErrorMessage).append(", "); // appends the error message to the StringBuilder
        }   
        CommonResponse errorResponse = CommonResponse.builder().hasError(true).error(String.valueOf(errorMessages)).message(String.valueOf(errorMessages)).status(HttpStatus.BAD_REQUEST).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class) // Handles REs
    @ResponseStatus(HttpStatus.BAD_REQUEST) // Sets the HTTP status to 400 BAD_REQUEST
    public ResponseEntity<?> handleRuntimeException(RuntimeException exception) { // Takes in the exception object
        // Builds a CommonResponse object with the exception message
        CommonResponse errorResponse = CommonResponse.builder().hasError(true).error(exception.getMessage()).message(exception.getMessage()).status(HttpStatus.BAD_REQUEST).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST); // Returns a ResponseEntity with the error response and the HTTP status
    }

    @ExceptionHandler(Exception.class) // Handles all exceptions of this type
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // Sets the HTTP status to 500 INTERNAL_SERVER_ERROR
    public ResponseEntity<?> handleException(Exception exception) { // Takes in the exception object
        CommonResponse errorResponse = CommonResponse.builder().hasError(true).error("Something went wrong").message("Something went wrong").status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST); // Returns a ResponseEntity with the error response and the HTTP status
    }
}
