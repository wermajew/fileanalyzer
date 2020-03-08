package com.merapar.fileanalyser.service;

import com.merapar.fileanalyser.exception.ResourceUnavailableException;
import com.merapar.fileanalyser.response.FileDetails;
import com.merapar.fileanalyser.response.ImmutableFileDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.math.BigInteger;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileProcessorServiceTest {

  private ReaderService readerService;

  private PostStatisticsProcessor postsStatisticsService;

  private XMLStreamReader mockXmlStreamReader;

  @BeforeEach
  void setUp() {
    this.readerService = mock(ReaderService.class);
    this.postsStatisticsService = mock(PostStatisticsProcessor.class);
    this.mockXmlStreamReader = mock(XMLStreamReader.class);
  }

  @Test
  void givenNoPostsInXmlShouldReturnEmptyFileDetails()
      throws JAXBException, XMLStreamException, ResourceUnavailableException {
    // given
    FileProcessorService fileProcessorService = new FileProcessorService(readerService);
    String file = "http://test-file.com/test.xml";

    // when
    when(readerService.getEventReader(file)).thenReturn(mockXmlStreamReader);
    when(postsStatisticsService.getFileDetails())
        .thenReturn(ImmutableFileDetails.builder().build());
    when(mockXmlStreamReader.hasNext()).thenReturn(false);
    FileDetails fileDetails = fileProcessorService.processFile(file, postsStatisticsService);

    // then
    assertThat(fileDetails.totalPosts()).isEqualTo(BigInteger.ZERO);
    assertThat(fileDetails.avgScore()).isEqualTo(0.0);
    assertThat(fileDetails.totalAcceptedPosts()).isEqualTo(BigInteger.ZERO);
  }

  @Test
  void givenResourceCannotBeAccessedShouldThrowException()
      throws ResourceUnavailableException, JAXBException {
    // given
    FileProcessorService fileProcessorService = new FileProcessorService(readerService);
    String file = "http://test-file.com/test.xml";

    // when
    when(readerService.getEventReader(file)).thenThrow(ResourceUnavailableException.class);
    Optional<FileDetails> fileDetails =
        fileProcessorService.processFile2(file, postsStatisticsService);

    // then
    assertFalse(fileDetails.isPresent());
  }
}
