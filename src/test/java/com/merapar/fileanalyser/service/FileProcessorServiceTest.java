package com.merapar.fileanalyser.service;

import com.merapar.fileanalyser.domain.Post;
import com.merapar.fileanalyser.exception.FileProcessingException;
import com.merapar.fileanalyser.exception.ResourceUnavailableException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class FileProcessorServiceTest {

  @InjectMocks private FileProcessorService fileProcessorService;

  @Mock private ReaderService readerService;

  @Mock private PostUnmarshaller postUnmarshaller;

  @Mock private StatisticsProcessor statisticsProcessor;

  @Test
  void givenFileCanBeReadAndXmlParsedShouldReturnFileDetails() {
    // given
    //  when
    // then
  }

  @Test
  void givenFileCannotBeReadShouldThrowException() {
    // given
    String file = "http://test.com/test.xml";

    // when
    when(readerService.getEventReader(file)).thenThrow(ResourceUnavailableException.class);

    // then
    assertThrows(FileProcessingException.class, () -> fileProcessorService.processFile(file));
  }

  @Test
  void givenFileCannotBeParsedShouldThrowException() throws XMLStreamException {
    // given
    String file = "http://test.com/test.xml";
    XMLStreamReader eventReaderMock = mock(XMLStreamReader.class);

    // when
    when(readerService.getEventReader(file)).thenReturn(eventReaderMock);
    when(eventReaderMock.hasNext()).thenReturn(true);
    when(readerService.canReadElement(eventReaderMock)).thenReturn(true);
    when(postUnmarshaller.unmarshal(eventReaderMock)).thenThrow(FileProcessingException.class);

    // then
    assertThrows(FileProcessingException.class, () -> fileProcessorService.processFile(file));
    verify(statisticsProcessor, times(0)).process(any(Post.class));
    verify(statisticsProcessor, times(0)).getFileDetails();
  }
}
