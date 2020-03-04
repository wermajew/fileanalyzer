package com.merapar.fileanalyser.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProccessFileRequest {

  @JsonProperty
  @NotNull(message = "url cannot be null")
  @Size(max = 10000, message = "url cannot be longer then 10000 characters")
  private String url;

  public String getUrl() {
    return url;
  }
}
