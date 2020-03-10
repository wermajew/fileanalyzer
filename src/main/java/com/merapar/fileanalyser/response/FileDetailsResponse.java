package com.merapar.fileanalyser.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.time.LocalDateTime;

@Value.Immutable
@JsonSerialize(as = ImmutableFileDetailsResponse.class)
public interface FileDetailsResponse {

  static FileDetailsResponse of(LocalDateTime localDateTime, FileDetails fileDetails) {
    return ImmutableFileDetailsResponse.of(localDateTime, fileDetails);
  }

  @Value.Parameter
  LocalDateTime analyseDate();

  @Value.Parameter
  FileDetails details();
}
