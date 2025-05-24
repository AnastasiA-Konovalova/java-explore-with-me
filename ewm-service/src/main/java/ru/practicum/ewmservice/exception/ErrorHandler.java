package ru.practicum.ewmservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

//    @ExceptionHandler(ValidationException.class)
//    public ResponseEntity<ErrorResponse> handlerValidationException(ValidationException e) {
//        log.error("Выброшено исключение, некорректный запрос: " + e.getMessage());
//        return new ResponseEntity<>(new ErrorResponse("Объект не найден: ", e.getMessage()), HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(ConflictException.class)
//    public ResponseEntity<ErrorResponse> handlerConflictException(ConflictException e) {
//        return new ResponseEntity<>(new ErrorResponse("Conflict exception", e.getMessage()), HttpStatus.CONFLICT);
//    }
//
//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity<ErrorResponse> handlerNotFoundException(NotFoundException e) {
//        return new ResponseEntity<>(new ErrorResponse("Not found exception", e.getMessage()), HttpStatus.NOT_FOUND);
//    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        return new ErrorResponse(
                "BAD_REQUEST",
                "Incorrectly made request.",
                String.format("Failed to convert value of type %s to required type %s; %s",
                        e.getValue(),
                        e.getRequiredType() != null ? e.getRequiredType().getSimpleName() : "unknown",
                        e.getMessage()),
                LocalDateTime.now()
        );
    }
}
