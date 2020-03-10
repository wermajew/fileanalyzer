package com.merapar.fileanalyser.exception;

public class FileProcessingException extends RuntimeException {

  public FileProcessingException(String errorMessage) {
    super(errorMessage);
  }

  public static FileProcessingException fromResource(String file) throws FileProcessingException {
    throw new FileProcessingException("Could not process file " + file);
  }
}
