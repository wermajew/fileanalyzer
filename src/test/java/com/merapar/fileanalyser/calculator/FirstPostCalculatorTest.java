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
class FirstPostCalculatorTest {

  @Autowired private FirstPostCalculator acceptedPostsCalculator;

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
  void givenPostHasCreationDateBeforeShouldReturnDetailsWithUpdatedFirstDate() {
    // given
    Post post = new Post();
    post.setAcceptedAnswerId(BigInteger.valueOf(12));
    post.setScore(BigInteger.valueOf(13));
    post.setCreationDate(LocalDateTime.of(2019, 1, 2, 11, 11));

    // when
    LocalDateTime expected = post.getCreationDate();
    FileDetails actualFileDetails = acceptedPostsCalculator.calculate(post, fileDetails);

    // then
    assertEquals(expected, actualFileDetails.firstPost());
  }

  @Test
  void givenPostHasCreationDateAfterReturnDetailsWithNotUpdatedFirstDate() {
    // given
    Post post = new Post();
    post.setAcceptedAnswerId(BigInteger.valueOf(12));
    post.setScore(BigInteger.valueOf(13));
    post.setCreationDate(LocalDateTime.of(2019, 1, 2, 12, 13));

    // when
    LocalDateTime expected = fileDetails.firstPost();
    FileDetails actualFileDetails = acceptedPostsCalculator.calculate(post, fileDetails);

    // then
    assertEquals(expected, actualFileDetails.firstPost());
  }
}
