package com.merapar.fileanalyser.service;

import com.merapar.fileanalyser.domain.Post;
import com.merapar.fileanalyser.response.FileDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class StatisticsProcessor {

  private FileDetails fileDetails;

  private List<FileStatisticsCalculator> statisticsCalculators;

  @Autowired
  public StatisticsProcessor(
      TotalScoreCalculator totalScoreCalculator,
      LastPostCalculator lastPostCalculator,
      FirstPostCalculator firstPostCalculator,
      AcceptedPostsCalculator acceptedPostsCalculator) {
    this.fileDetails = FileDetails.of();
    this.statisticsCalculators =
        Arrays.asList(
            totalScoreCalculator, lastPostCalculator, firstPostCalculator, acceptedPostsCalculator);
    this.fileDetails = FileDetails.of();
  }

  public FileDetails process(Post post) {
    statisticsCalculators.forEach(i -> fileDetails = i.calculate(post, fileDetails));
    return fileDetails;
  }

  public FileDetails getFileDetails() {
    return FileDetails.of(fileDetails);
  }

  public void setFileDetails(FileDetails fileDetails) {
    this.fileDetails = fileDetails;
  }
}
