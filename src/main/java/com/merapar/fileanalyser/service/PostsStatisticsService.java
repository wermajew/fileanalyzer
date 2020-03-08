package com.merapar.fileanalyser.service;

import com.merapar.fileanalyser.domain.Post;
import com.merapar.fileanalyser.response.FileDetails;
import com.merapar.fileanalyser.response.ImmutableFileDetails;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class PostsStatisticsService implements PostStatisticsProcessor {

  private FileDetails fileDetails;

  private BigInteger totalScore = BigInteger.ZERO;

  public PostsStatisticsService() {
    this.fileDetails = FileDetails.of();
  }

  public PostsStatisticsService updateTotalPosts(Post post) {
    if (post != null) {
      BigInteger totalPosts = fileDetails.totalPosts().add(BigInteger.ONE);
      fileDetails = ImmutableFileDetails.copyOf(fileDetails).withTotalPosts(totalPosts);
    }
    return this;
  }

  public PostsStatisticsService updateTotalScore(Post post) {
    if (post.getScore() != null) {
      totalScore = totalScore.add(post.getScore());
    }
    return this;
  }

  public PostsStatisticsService updateTotalAcceptedPosts(Post post) {
    if (post.getAcceptedAnswerId() != null) {
      BigInteger totalAcceptedPosts = fileDetails.totalAcceptedPosts().add(BigInteger.ONE);
      fileDetails =
          ImmutableFileDetails.copyOf(fileDetails).withTotalAcceptedPosts(totalAcceptedPosts);
    }
    return this;
  }

  public PostsStatisticsService updateLastPost(Post post) {
    if (post.getCreationDate() != null) {
      if (post.getCreationDate().isAfter(fileDetails.lastPost())) {
        fileDetails = ImmutableFileDetails.copyOf(fileDetails).withLastPost(post.getCreationDate());
      }
    }
    return this;
  }

  public PostsStatisticsService updateFirstPost(Post post) {
    if (post.getCreationDate() != null) {
      if (post.getCreationDate().isBefore(fileDetails.firstPost())) {
        fileDetails =
            ImmutableFileDetails.copyOf(fileDetails).withFirstPost(post.getCreationDate());
      }
    }
    return this;
  }

  public FileDetails getFileDetails() {
    return ImmutableFileDetails.copyOf(fileDetails)
        .withAvgScore(totalScore.doubleValue() / fileDetails.totalPosts().doubleValue());
  }

  public PostsStatisticsService processStatistics(Post post) {
    return updateTotalPosts(post)
        .updateTotalScore(post)
        .updateFirstPost(post)
        .updateLastPost(post)
        .updateTotalAcceptedPosts(post);
  }
}
