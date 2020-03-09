package com.merapar.fileanalyser.service;

import com.merapar.fileanalyser.domain.Post;
import com.merapar.fileanalyser.response.FileDetails;

public interface FileStatisticsCalculator {

  FileDetails calculate(Post post, FileDetails fileDetails);
}
