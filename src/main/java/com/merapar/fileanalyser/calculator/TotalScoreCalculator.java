package com.merapar.fileanalyser.calculator;

import com.merapar.fileanalyser.domain.Post;
import com.merapar.fileanalyser.response.FileDetails;
import com.merapar.fileanalyser.response.ImmutableFileDetails;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class TotalScoreCalculator implements FileStatisticsCalculator {

  @Override
  public FileDetails calculate(Post post, FileDetails details) {
    BigInteger totalPosts = details.totalPosts().add(BigInteger.ONE);
    if (post.getScore() != null) {
      BigInteger totalScore = details.totalScore().add(post.getScore());
      return ImmutableFileDetails.copyOf(details)
          .withTotalScore(totalScore)
          .withTotalPosts(totalPosts);
    }
    return ImmutableFileDetails.copyOf(details).withTotalPosts(totalPosts);
  }
}
