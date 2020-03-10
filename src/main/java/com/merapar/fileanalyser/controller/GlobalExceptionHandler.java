package com.merapar.fileanalyser.controller;

import com.merapar.fileanalyser.exception.FileProcessingException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(FileProcessingException.class)
  protected ResponseEntity<Object> handleError(RuntimeException ex, WebRequest request) {
    String bodyOfResponse = "Could not process file";
    return handleExceptionInternal(
        ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
  }
}
