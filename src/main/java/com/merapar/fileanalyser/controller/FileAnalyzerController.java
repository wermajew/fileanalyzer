package com.merapar.fileanalyser.controller;

import com.merapar.fileanalyser.request.ImmutableProcessFileRequest;
import com.merapar.fileanalyser.response.FileDetails;
import com.merapar.fileanalyser.response.ImmutableFileDetailsResponse;
import com.merapar.fileanalyser.service.FileProcessorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class FileAnalyzerController {

  private static final Logger LOG = LoggerFactory.getLogger(FileAnalyzerController.class);

  private FileProcessorService fileProcessorService;

  @Autowired
  public FileAnalyzerController(FileProcessorService fileProcessorService) {
    this.fileProcessorService = fileProcessorService;
  }

  @RequestMapping(value = "/analyze", method = RequestMethod.POST)
  public ImmutableFileDetailsResponse analyzeFile(
      @RequestBody ImmutableProcessFileRequest processFileRequest) {
    LOG.info("Process file request: {}", processFileRequest);
    LocalDateTime now = LocalDateTime.now();
    FileDetails fileDetails = fileProcessorService.processFile(processFileRequest.url());
    ImmutableFileDetailsResponse response = ImmutableFileDetailsResponse.of(now, fileDetails);
    LOG.info("Calculated result {}", fileDetails);
    return response;
  }
}
