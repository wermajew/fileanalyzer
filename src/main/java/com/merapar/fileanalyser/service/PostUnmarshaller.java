package com.merapar.fileanalyser.service;

import com.merapar.fileanalyser.domain.Post;
import com.merapar.fileanalyser.exception.FileProcessingException;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamReader;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PostUnmarshaller {

  private final Unmarshaller unmarshaller;

  public PostUnmarshaller() throws JAXBException {
    this.unmarshaller = JAXBContext.newInstance(Post.class).createUnmarshaller();
  }

  public Post unmarshal(XMLStreamReader eventReader) {
    try {
      return unmarshaller.unmarshal(eventReader, Post.class).getValue();
    } catch (JAXBException e) {
      throw new FileProcessingException("Could not process element " + eventReader.getLocalName());
    }
  }
}
