package com.merapar.fileanalyser.exception;

public class FileUnprocessableException extends RuntimeException {

  public FileUnprocessableException(String errorMessage) {
    super(errorMessage);
  }

  public static FileUnprocessableException fromResource(String file)
      throws FileUnprocessableException {
    throw new FileUnprocessableException("Could not process file " + file);
  }
}
