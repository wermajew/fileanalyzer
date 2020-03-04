package com.merapar.fileanalyser.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class FileDetailsWrapperResponse {

  @JsonProperty private LocalDateTime analyseDate;

  @JsonProperty private FileDetails details;
}
