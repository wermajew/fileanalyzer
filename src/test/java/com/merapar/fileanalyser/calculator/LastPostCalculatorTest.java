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
class LastPostCalculatorTest {

  @Autowired private LastPostCalculator lastPostCalculator;

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
  void givenPostHasCreationDateAfterShouldReturnDetailsWithUpdatedLastDate() {
    // given
    Post post = new Post();
    post.setAcceptedAnswerId(BigInteger.valueOf(12));
    post.setScore(BigInteger.valueOf(13));
    post.setCreationDate(LocalDateTime.of(2021, 1, 2, 12, 20));

    // when
    LocalDateTime expected = post.getCreationDate();
    FileDetails actualFileDetails = lastPostCalculator.calculate(post, fileDetails);

    // then
    assertEquals(expected, actualFileDetails.lastPost());
  }

  @Test
  void givenPostHasCreationDateBeforeReturnDetailsWithNotUpdatedLastDate() {
    // given
    Post post = new Post();
    post.setAcceptedAnswerId(BigInteger.valueOf(12));
    post.setScore(BigInteger.valueOf(13));
    post.setCreationDate(LocalDateTime.of(2019, 1, 2, 10, 10));

    // when
    LocalDateTime expected = fileDetails.lastPost();
    FileDetails actualFileDetails = lastPostCalculator.calculate(post, fileDetails);

    // then
    assertEquals(expected, actualFileDetails.lastPost());
  }
}
