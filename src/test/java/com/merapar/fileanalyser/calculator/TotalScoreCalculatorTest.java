package com.merapar.fileanalyser.calculator;

import com.merapar.fileanalyser.domain.Post;
import com.merapar.fileanalyser.response.FileDetails;
import com.merapar.fileanalyser.response.ImmutableFileDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.time.LocalDateTime;

import static junit.framework.TestCase.assertEquals;

@SpringBootTest
class TotalScoreCalculatorTest {

  @Autowired private TotalScoreCalculator totalScoreCalculator;

  private FileDetails fileDetails;

  @BeforeEach
  void setUp() {
    this.fileDetails =
        ImmutableFileDetails.builder()
            .totalPosts(BigInteger.valueOf(10))
            .totalAcceptedPosts(BigInteger.valueOf(11))
            .totalPosts(BigInteger.valueOf(13))
            .totalScore(BigInteger.valueOf(20))
            .firstPost(LocalDateTime.of(2019, 1, 2, 12, 12))
            .lastPost(LocalDateTime.of(2020, 1, 2, 12, 12))
            .build();
  }

  @Test
  void givenPostHasScoreShouldIncrementTotalPostsAndScore() {
    // given
    Post post = new Post();
    post.setAcceptedAnswerId(BigInteger.valueOf(12));
    post.setScore(BigInteger.valueOf(13));
    post.setCreationDate(LocalDateTime.of(2019, 1, 2, 12, 12));

    // when
    BigInteger expectedTotalPosts = BigInteger.valueOf(14);
    BigInteger expectedTotalScore = BigInteger.valueOf(33);
    FileDetails actualFileDetails = totalScoreCalculator.calculate(post, fileDetails);

    // then
    assertEquals(expectedTotalPosts, actualFileDetails.totalPosts());
    assertEquals(expectedTotalScore, actualFileDetails.totalScore());
  }

  @Test
  void givenPost() {
    // given
    Post post = new Post();
    post.setAcceptedAnswerId(BigInteger.valueOf(12));
    post.setScore(null);
    post.setCreationDate(LocalDateTime.of(2019, 1, 2, 12, 12));

    // when
    BigInteger expectedTotalPosts = BigInteger.valueOf(14);
    BigInteger expectedTotalScore = fileDetails.totalScore();
    FileDetails actualFileDetails = totalScoreCalculator.calculate(post, fileDetails);

    // then
    assertEquals(expectedTotalPosts, actualFileDetails.totalPosts());
    assertEquals(expectedTotalScore, actualFileDetails.totalScore());
  }
}
