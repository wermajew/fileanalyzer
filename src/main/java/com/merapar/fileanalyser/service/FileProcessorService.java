package com.merapar.fileanalyser.service;

import com.merapar.fileanalyser.domain.Post;
import com.merapar.fileanalyser.exception.FileUnprocessableException;
import com.merapar.fileanalyser.exception.ResourceUnavailableException;
import com.merapar.fileanalyser.response.FileDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.Optional;

@Service
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class FileProcessorService {

  private static final Logger LOG = LoggerFactory.getLogger(FileProcessorService.class);

  private static final String ROW_NAME = "row";

  private final StatisticsProcessor statisticsProcessor;

  private final Unmarshaller unmarshaller;

  private final ReaderService readerService;

  public FileProcessorService(ReaderService readerService, StatisticsProcessor statisticsProcessor)
      throws JAXBException {
    this.readerService = readerService;
    this.unmarshaller = JAXBContext.newInstance(Post.class).createUnmarshaller();
    this.statisticsProcessor = statisticsProcessor;
  }

  public FileDetails processFile(String file) {
    try {
      final XMLStreamReader eventReader = readerService.getEventReader(file);
      statisticsProcessor.setFileDetails(FileDetails.of());
      while (eventReader.hasNext()) {
        eventReader.next();
        if (eventReader.getEventType() == XMLStreamConstants.START_ELEMENT
            && ROW_NAME.equals(eventReader.getLocalName())) {
          final Post post = unmarshaller.unmarshal(eventReader, Post.class).getValue();
          statisticsProcessor.process(post);
        }
      }
    } catch (ResourceUnavailableException | JAXBException | XMLStreamException e) {
      LOG.error("Could not process file");
      throw FileUnprocessableException.fromResource(file);
    }
    return statisticsProcessor.getFileDetails();
  }
}
