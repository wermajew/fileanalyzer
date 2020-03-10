package com.merapar.fileanalyser.service;

import com.merapar.fileanalyser.domain.Post;
import com.merapar.fileanalyser.exception.FileProcessingException;
import com.merapar.fileanalyser.exception.ResourceUnavailableException;
import com.merapar.fileanalyser.response.FileDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class FileProcessorService {

  private static final Logger LOG = LoggerFactory.getLogger(FileProcessorService.class);

  private static final String ROW_NAME = "row";

  private final StatisticsProcessor statisticsProcessor;

  private final PostUnmarshaller unmarshaller;

  private final ReaderService readerService;

  public FileProcessorService(
      ReaderService readerService,
      StatisticsProcessor statisticsProcessor,
      PostUnmarshaller unmarshaller) {
    this.readerService = readerService;
    this.statisticsProcessor = statisticsProcessor;
    this.unmarshaller = unmarshaller;
  }

  public FileDetails processFile(final String file) {
    try {
      final XMLStreamReader eventReader = readerService.getEventReader(file);
      while (eventReader.hasNext()) {
        eventReader.next();
        if (readerService.canReadElement(eventReader)) {
          final Post post = unmarshaller.unmarshal(eventReader);
          statisticsProcessor.process(post);
        }
      }
    } catch (ResourceUnavailableException | XMLStreamException e) {
      LOG.error("Could not process file");
      throw FileProcessingException.fromResource(file);
    }
    return statisticsProcessor.getFileDetails();
  }
}
