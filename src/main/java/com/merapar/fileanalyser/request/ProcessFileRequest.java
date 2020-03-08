package com.merapar.fileanalyser.request;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value.Immutable
@JsonSerialize(as = ImmutableProcessFileRequest.class)
public interface ProcessFileRequest {

  @NotNull(message = "url cannot be null")
  @Size(max = 10000, message = "url cannot be longer then 10000 characters")
  @Value.Parameter
  String url();
}
