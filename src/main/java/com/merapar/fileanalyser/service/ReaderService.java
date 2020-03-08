package com.merapar.fileanalyser.service;

import com.merapar.fileanalyser.exception.ResourceUnavailableException;
import org.springframework.stereotype.Component;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.net.URL;

@Component
public class ReaderService {

  public XMLStreamReader getEventReader(String file) throws ResourceUnavailableException {
    try {
      return XMLInputFactory.newInstance().createXMLStreamReader(new URL(file).openStream());
    } catch (XMLStreamException | IOException e) {
      throw new ResourceUnavailableException("Cannot access file");
    }
  }
}
