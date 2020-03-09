package com.merapar.fileanalyser.service;

import com.merapar.fileanalyser.domain.Post;
import com.merapar.fileanalyser.response.FileDetails;
import com.merapar.fileanalyser.response.ImmutableFileDetails;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class TotalScoreCalculator implements FileStatisticsCalculator {

  @Override
  public FileDetails calculate(Post post, FileDetails details) {
    if (post.getScore() != null) {
      BigInteger totalScore = details.totalScore().add(post.getScore());
      BigInteger totalPosts = details.totalPosts().add(BigInteger.ONE);
      return ImmutableFileDetails.copyOf(details)
          .withTotalScore(totalScore)
          .withTotalPosts(totalPosts);
    }
    return details;
  }
}
