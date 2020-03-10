package com.merapar.fileanalyser.service;

import com.merapar.fileanalyser.exception.ResourceUnavailableException;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.net.URL;
import java.util.function.Predicate;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ReaderService {

  private static final String ROW_NAME = "row";

  private final Predicate<XMLStreamReader> canProcessElement =
      e ->
          e.getEventType() == XMLStreamConstants.START_ELEMENT && ROW_NAME.equals(e.getLocalName());

  public XMLStreamReader getEventReader(String file) {
    try {
      return XMLInputFactory.newInstance().createXMLStreamReader(new URL(file).openStream());
    } catch (XMLStreamException | IOException e) {
      throw ResourceUnavailableException.fromResource("Cannot access file");
    }
  }

  public boolean canReadElement(XMLStreamReader eventReader) {
    return canProcessElement.test(eventReader);
  }
}
