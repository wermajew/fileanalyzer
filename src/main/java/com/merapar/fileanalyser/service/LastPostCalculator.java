package com.merapar.fileanalyser.service;

import com.merapar.fileanalyser.domain.Post;
import com.merapar.fileanalyser.response.FileDetails;
import com.merapar.fileanalyser.response.ImmutableFileDetails;
import org.springframework.stereotype.Component;

@Component
public class LastPostCalculator implements FileStatisticsCalculator {

  @Override
  public FileDetails calculate(Post post, FileDetails details) {
    if (post.getCreationDate() != null) {
      if (post.getCreationDate().isAfter(details.lastPost())) {
        return ImmutableFileDetails.copyOf(details).withLastPost(post.getCreationDate());
      }
    }
    return details;
  }
}
