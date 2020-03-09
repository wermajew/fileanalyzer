package com.merapar.fileanalyser.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {

  private DateTimeFormatter dateFormat = DateTimeFormatter.ISO_DATE_TIME;

  @Override
  public String marshal(LocalDateTime dateTime) {
    return dateTime.format(dateFormat);
  }

  @Override
  public LocalDateTime unmarshal(String dateTime) {
    return LocalDateTime.parse(dateTime, dateFormat);
  }
}
