package com.merapar.fileanalyser.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;

@Value.Immutable
@JsonSerialize(as = ImmutableFileDetailsResponse.class)
public interface FileDetailsResponse {

  @Value.Parameter
  @Nonnull
  LocalDateTime analyseDate();

  @Value.Parameter
  @Nonnull
  FileDetails details();

  static FileDetailsResponse of(LocalDateTime localDateTime, FileDetails fileDetails) {
    return ImmutableFileDetailsResponse.of(localDateTime, fileDetails);
  }
}
