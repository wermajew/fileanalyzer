package com.merapar.fileanalyser.exception;

public class ResourceUnavailableException extends Exception {

  public ResourceUnavailableException(String errorMessage) {
    super(errorMessage);
  }

  public static ResourceUnavailableException fromResource(String resource)
      throws ResourceUnavailableException {
    throw new ResourceUnavailableException("Could not access resource " + resource);
  }
}
