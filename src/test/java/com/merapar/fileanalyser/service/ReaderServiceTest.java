package com.merapar.fileanalyser.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class ReaderServiceTest {

  @Autowired private ReaderService readerService;

  @Mock private XMLStreamReader xmlStreamReader;

  @Test
  void givenElementIsValidWhenCanProcessFileShouldReturnTrue() {
    when(xmlStreamReader.getEventType()).thenReturn(XMLStreamConstants.START_ELEMENT);
    when(xmlStreamReader.getLocalName()).thenReturn("row");

    assertTrue(readerService.canReadElement(xmlStreamReader));
  }

  @Test
  void givenElementIsValidWhenCanProcessFileShouldReturnFalse() {
    when(xmlStreamReader.getEventType()).thenReturn(XMLStreamConstants.START_ELEMENT);
    when(xmlStreamReader.getLocalName()).thenReturn("notrow");

    assertFalse(readerService.canReadElement(xmlStreamReader));
  }

  @Test
  void givenElementIsValidAndElementNotStartElementWhenCanProcessFileShouldReturnFalse() {
    when(xmlStreamReader.getEventType()).thenReturn(XMLStreamConstants.END_ELEMENT);
    when(xmlStreamReader.getLocalName()).thenReturn("row");

    assertFalse(readerService.canReadElement(xmlStreamReader));
  }
}
