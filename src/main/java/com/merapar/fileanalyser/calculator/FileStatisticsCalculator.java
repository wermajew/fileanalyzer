package com.merapar.fileanalyser.calculator;

import com.merapar.fileanalyser.domain.Post;
import com.merapar.fileanalyser.response.FileDetails;

public interface FileStatisticsCalculator {

  FileDetails calculate(Post post, FileDetails fileDetails);
}
