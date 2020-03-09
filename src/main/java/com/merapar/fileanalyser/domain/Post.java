package com.merapar.fileanalyser.domain;

import com.merapar.fileanalyser.adapter.LocalDateTimeAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigInteger;
import java.time.LocalDateTime;

@XmlAccessorType(XmlAccessType.FIELD)
public class Post {

  @XmlAttribute(name = "Score")
  private BigInteger score;

  @XmlAttribute(name = "AcceptedAnswerId")
  private BigInteger acceptedAnswerId;

  @XmlAttribute(name = "CreationDate")
  @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
  private LocalDateTime creationDate;

  public Post() {}

  public Post(BigInteger score, BigInteger acceptedAnswerId, LocalDateTime creationDate) {
    this.score = score;
    this.acceptedAnswerId = acceptedAnswerId;
    this.creationDate = creationDate;
  }

  public BigInteger getScore() {
    return score;
  }

  public BigInteger getAcceptedAnswerId() {
    return acceptedAnswerId;
  }

  public LocalDateTime getCreationDate() {
    return creationDate;
  }

  public void setScore(BigInteger score) {
    this.score = score;
  }

  public void setAcceptedAnswerId(BigInteger acceptedAnswerId) {
    this.acceptedAnswerId = acceptedAnswerId;
  }

  public void setCreationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
  }
}
