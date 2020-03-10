package com.merapar.fileanalyser.service;

import com.merapar.fileanalyser.calculator.AcceptedPostsCalculator;
import com.merapar.fileanalyser.calculator.FileStatisticsCalculator;
import com.merapar.fileanalyser.calculator.FirstPostCalculator;
import com.merapar.fileanalyser.calculator.LastPostCalculator;
import com.merapar.fileanalyser.calculator.TotalScoreCalculator;
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

  public void process(Post post) {
    statisticsCalculators.forEach(i -> fileDetails = i.calculate(post, fileDetails));
  }

  public FileDetails getFileDetails() {
    return FileDetails.of(fileDetails);
  }
}
