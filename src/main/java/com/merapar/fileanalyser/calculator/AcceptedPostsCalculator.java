package com.merapar.fileanalyser.calculator;

import com.merapar.fileanalyser.domain.Post;
import com.merapar.fileanalyser.response.FileDetails;
import com.merapar.fileanalyser.response.ImmutableFileDetails;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class AcceptedPostsCalculator implements FileStatisticsCalculator {

  @Override
  public FileDetails calculate(Post post, FileDetails details) {
    if (post.getAcceptedAnswerId() != null) {
      BigInteger totalAcceptedPosts = details.totalAcceptedPosts().add(BigInteger.ONE);
      return ImmutableFileDetails.copyOf(details).withTotalAcceptedPosts(totalAcceptedPosts);
    }
    return details;
  }
}
