package com.merapar.fileanalyser.controller;

import com.merapar.fileanalyser.request.ProccessFileRequest;
import com.merapar.fileanalyser.response.FileDetailsWrapperResponse;
import com.merapar.fileanalyser.service.FileProcessorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class FileAnalyzerController {

  private static final Logger LOG = LoggerFactory.getLogger(FileAnalyzerController.class);

  private FileProcessorService fileProcessorService;

  public FileAnalyzerController(FileProcessorService fileProcessorService) {
    this.fileProcessorService = fileProcessorService;
  }

  @PostMapping("/analyze")
  public FileDetailsWrapperResponse analyseFile(
      @RequestBody ProccessFileRequest proccessFileRequest) {
    LOG.info("Process file request: {}", proccessFileRequest);
    fileProcessorService.processFile(proccessFileRequest.getUrl());
    return null;
  }
}
