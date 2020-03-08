package com.merapar.fileanalyser.service;

import com.merapar.fileanalyser.domain.Post;
import com.merapar.fileanalyser.response.FileDetails;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

class PostsStatisticsServiceTest {

  @Test
  void givenFirstPostProvidedWhenProcessShouldCalculateStatistics() {
    // given
    PostsStatisticsService postsStatisticsService = new PostsStatisticsService();
    Post post =
        new Post(
            BigInteger.valueOf(12L), BigInteger.valueOf(13L), LocalDateTime.of(2012, 2, 3, 0, 0));

    // when
    postsStatisticsService.processStatistics(post);
    FileDetails fileDetails = postsStatisticsService.getFileDetails();

    // then
    assertEquals(fileDetails.totalPosts(), BigInteger.ONE);
    assertEquals(fileDetails.avgScore(), Double.valueOf(12));
    assertEquals(fileDetails.lastPost(), post.getCreationDate());
    assertEquals(fileDetails.firstPost(), post.getCreationDate());
  }

  @Test
  void given3PostProvidedWhenProcessShouldCalculateStatistics() {
    // given
    PostsStatisticsService postsStatisticsService = new PostsStatisticsService();
    Post firstPost =
        new Post(
            BigInteger.valueOf(15L), BigInteger.valueOf(13L), LocalDateTime.of(2012, 2, 3, 0, 0));

    Post secondPost =
        new Post(
            BigInteger.valueOf(20L), BigInteger.valueOf(14L), LocalDateTime.of(2013, 2, 3, 0, 0));

    Post thirdPost =
        new Post(
            BigInteger.valueOf(30L), BigInteger.valueOf(15L), LocalDateTime.of(2014, 2, 3, 0, 0));

    Post fourthPost = new Post(BigInteger.valueOf(30L), BigInteger.valueOf(15L), null);

    // when
    postsStatisticsService.processStatistics(firstPost);
    postsStatisticsService.processStatistics(secondPost);
    postsStatisticsService.processStatistics(thirdPost);
    postsStatisticsService.processStatistics(fourthPost);

    // and
    FileDetails fileDetails = postsStatisticsService.getFileDetails();

    // then
    assertEquals(BigInteger.valueOf(4), fileDetails.totalPosts());
    assertEquals(Double.valueOf(23.75), fileDetails.avgScore());
    assertEquals(fileDetails.lastPost(), thirdPost.getCreationDate());
    assertEquals(fileDetails.firstPost(), firstPost.getCreationDate());
  }
}
