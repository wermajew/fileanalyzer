package com.merapar.fileanalyser.calculator;

import com.merapar.fileanalyser.domain.Post;
import com.merapar.fileanalyser.response.FileDetails;
import com.merapar.fileanalyser.response.ImmutableFileDetails;
import org.springframework.stereotype.Component;

@Component
public class FirstPostCalculator implements FileStatisticsCalculator {

  @Override
  public FileDetails calculate(Post post, FileDetails details) {
    if (post.getCreationDate() != null) {
      if (post.getCreationDate().isBefore(details.firstPost())) {
        return ImmutableFileDetails.copyOf(details).withFirstPost(post.getCreationDate());
      }
    }
    return details;
  }
}
