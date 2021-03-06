package com.merapar.fileanalyser.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Value.Immutable
@JsonSerialize(as = ImmutableFileDetails.class)
public interface FileDetails {

  static FileDetails of(FileDetails f) {
    return ImmutableFileDetails.builder()
        .avgScore(f.totalScore().doubleValue() / f.totalPosts().doubleValue())
        .firstPost(f.firstPost())
        .lastPost(f.lastPost())
        .totalPosts(f.totalPosts())
        .totalAcceptedPosts(f.totalAcceptedPosts())
        .totalScore(f.totalScore())
        .build();
  }

  static FileDetails of() {
    return ImmutableFileDetails.builder().build();
  }

  @Value.Default
  default LocalDateTime firstPost() {
    return LocalDateTime.MAX;
  }

  @Value.Default
  default LocalDateTime lastPost() {
    return LocalDateTime.MIN;
  }

  @Value.Default
  default BigInteger totalPosts() {
    return BigInteger.ZERO;
  }

  @Value.Default
  default BigInteger totalScore() {
    return BigInteger.ZERO;
  }

  @Value.Default
  default BigInteger totalAcceptedPosts() {
    return BigInteger.ZERO;
  }

  @Value.Default
  default Double avgScore() {
    return (double) 0;
  }
}
