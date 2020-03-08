package com.merapar.fileanalyser.service;

import com.merapar.fileanalyser.domain.Post;
import com.merapar.fileanalyser.exception.ResourceUnavailableException;
import com.merapar.fileanalyser.response.FileDetails;
import com.sun.media.sound.InvalidDataException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.util.Optional;

@Service
public class FileProcessorService implements FileProcessor {

  private static final Logger LOG = LoggerFactory.getLogger(FileProcessorService.class);

  private static final String ROW_NAME = "row";

  private final Unmarshaller unmarshaller;

  private final ReaderService readerService;

  public FileProcessorService(ReaderService readerService) throws JAXBException {
    this.readerService = readerService;
    this.unmarshaller = JAXBContext.newInstance(Post.class).createUnmarshaller();
  }

  public FileDetails processFile(String file, PostStatisticsProcessor postsStatisticsService) {
    try {
      XMLStreamReader eventReader = readerService.getEventReader(file);
      while (eventReader.hasNext()) {
        eventReader.next();
        if (eventReader.getEventType() == XMLStreamConstants.START_ELEMENT
            && ROW_NAME.equals(eventReader.getLocalName())) {
          try {
            Post post = unmarshaller.unmarshal(eventReader, Post.class).getValue();
            postsStatisticsService.processStatistics(post);
          } catch (JAXBException e) {
            LOG.error("Cannot unmarshall XML tag", e);
            throw new InvalidDataException("");
          }
        }
      }
      return postsStatisticsService.getFileDetails();
    } catch (XMLStreamException | IOException | ResourceUnavailableException e) {
      throw new ResourceNotFoundException("Could not fetch remote resource");
    }
  }

  public Optional<FileDetails> processFile2(
      String file, PostStatisticsProcessor postsStatisticsService) {
    try {
      XMLStreamReader eventReader = readerService.getEventReader(file);
      while (eventReader.hasNext()) {
        eventReader.next();
        if (eventReader.getEventType() == XMLStreamConstants.START_ELEMENT
            && ROW_NAME.equals(eventReader.getLocalName())) {
          Post post = unmarshaller.unmarshal(eventReader, Post.class).getValue();
          postsStatisticsService.processStatistics(post);
        }
      }
    } catch (ResourceUnavailableException | JAXBException | XMLStreamException e) {
      LOG.error("Could not process file");
      return Optional.empty();
    }
    return Optional.of(postsStatisticsService.getFileDetails());
  }
}
