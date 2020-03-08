package com.merapar.fileanalyser.service;

import com.merapar.fileanalyser.domain.Post;
import com.merapar.fileanalyser.response.FileDetails;

public interface PostStatisticsProcessor {

  PostStatisticsProcessor processStatistics(Post post);

  FileDetails getFileDetails();
}
