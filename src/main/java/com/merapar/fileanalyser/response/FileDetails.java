package com.merapar.fileanalyser.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class FileDetails {

  @JsonProperty
  private LocalDateTime firstPost;

  @JsonProperty
  private LocalDateTime lastPost;

  @JsonProperty
  private LocalDateTime totalPosts;

  @JsonProperty
  private LocalDateTime totalAcceptedPosts;

  @JsonProperty
  private LocalDateTime avgScore;
}
