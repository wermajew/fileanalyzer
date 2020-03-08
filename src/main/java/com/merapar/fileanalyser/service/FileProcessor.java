package com.merapar.fileanalyser.service;

import com.merapar.fileanalyser.response.FileDetails;

public interface FileProcessor {

  FileDetails processFile(String file, PostStatisticsProcessor postStatisticsProcessor);
}
