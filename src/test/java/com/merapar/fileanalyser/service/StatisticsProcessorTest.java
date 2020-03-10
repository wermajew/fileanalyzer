package com.merapar.fileanalyser.service;

import com.merapar.fileanalyser.calculator.AcceptedPostsCalculator;
import com.merapar.fileanalyser.calculator.FirstPostCalculator;
import com.merapar.fileanalyser.calculator.LastPostCalculator;
import com.merapar.fileanalyser.calculator.TotalScoreCalculator;
import com.merapar.fileanalyser.domain.Post;
import com.merapar.fileanalyser.response.FileDetails;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class StatisticsProcessorTest {

  @InjectMocks private StatisticsProcessor statisticsProcessor;

  @Mock private TotalScoreCalculator totalScoreCalculator;

  @Mock private LastPostCalculator lastPostCalculator;

  @Mock private FirstPostCalculator firstPostCalculator;

  @Mock private AcceptedPostsCalculator acceptedPostsCalculator;

  @Test
  void whenProcessRunShouldReturnFileDetails() {
    // given
    Post post = new Post();
    post.setAcceptedAnswerId(BigInteger.valueOf(12));
    post.setScore(BigInteger.valueOf(13));
    post.setCreationDate(LocalDateTime.of(2019, 1, 2, 12, 12));

    FileDetails inputDetails = FileDetails.of();
    FileDetails outputDetails = FileDetails.of();

    // when
    when(lastPostCalculator.calculate(post, inputDetails)).thenReturn(outputDetails);
    when(firstPostCalculator.calculate(post, inputDetails)).thenReturn(outputDetails);
    when(totalScoreCalculator.calculate(post, inputDetails)).thenReturn(outputDetails);
    when(acceptedPostsCalculator.calculate(post, inputDetails)).thenReturn(outputDetails);

    statisticsProcessor.process(post);

    // then
    verify(lastPostCalculator).calculate(post, inputDetails);
    verify(firstPostCalculator).calculate(post, inputDetails);
    verify(totalScoreCalculator).calculate(post, inputDetails);
    verify(acceptedPostsCalculator).calculate(post, inputDetails);
  }
}
