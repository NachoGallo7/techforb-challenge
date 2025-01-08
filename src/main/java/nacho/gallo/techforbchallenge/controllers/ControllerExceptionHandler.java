package nacho.gallo.techforbchallenge.controllers;

import nacho.gallo.techforbchallenge.dtos.common.ErrorApiDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorApiDTO> handleError(Exception exception) {
    ErrorApiDTO error = buildError(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }

  @ExceptionHandler(ResponseStatusException.class)
  public ResponseEntity<ErrorApiDTO> handleError(ResponseStatusException exception) {
    ErrorApiDTO error = buildError(exception.getReason(), HttpStatus.valueOf(exception.getStatusCode().value()));
    return ResponseEntity.status(exception.getStatusCode()).body(error);
  }

  private final ErrorApiDTO buildError(String message, HttpStatus httpStatus) {
    return ErrorApiDTO.builder()
        .timestamp(LocalDateTime.now().toString())
        .status(httpStatus.value())
        .error(httpStatus.getReasonPhrase())
        .message(message)
        .build();
  }
}
