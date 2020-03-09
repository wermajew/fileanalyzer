package com.merapar.fileanalyser.service;

import com.merapar.fileanalyser.exception.ResourceUnavailableException;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.net.URL;

@Component
@Scope(value="request", proxyMode= ScopedProxyMode.TARGET_CLASS)
public class ReaderService {

  public XMLStreamReader getEventReader(String file) {
    try {
      return XMLInputFactory.newInstance().createXMLStreamReader(new URL(file).openStream());
    } catch (XMLStreamException | IOException e) {
      throw ResourceUnavailableException.fromResource("Cannot access file");
    }
  }
}
