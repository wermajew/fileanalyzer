package com.merapar.fileanalyser.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.merapar.fileanalyser.exception.FileProcessingException;
import com.merapar.fileanalyser.request.ImmutableProcessFileRequest;
import com.merapar.fileanalyser.response.FileDetails;
import com.merapar.fileanalyser.response.ImmutableFileDetails;
import com.merapar.fileanalyser.service.FileProcessorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.math.BigInteger;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FileAnalyzerControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private FileProcessorService fileProcessorService;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void givenUrlProvidedShouldReturn200Ok() throws Exception {
    // given
    String url = "http://test.com/resource";
    ImmutableProcessFileRequest processFileRequest = ImmutableProcessFileRequest.of(url);

    // when
    FileDetails fileDetails =
        ImmutableFileDetails.builder()
            .avgScore(5.6)
            .firstPost(LocalDateTime.of(2015, 1, 2, 12, 12))
            .lastPost(LocalDateTime.of(2020, 1, 2, 12, 12))
            .totalAcceptedPosts(BigInteger.valueOf(15))
            .totalPosts(BigInteger.valueOf(100))
            .build();

    // and
    when(fileProcessorService.processFile(url)).thenReturn(fileDetails);

    mockMvc
        .perform(
            post("/analyze")
                .header("Content-Type", "application/json")
                .content(objectMapper.writeValueAsBytes(processFileRequest)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.analyseDate").exists())
        .andExpect(jsonPath("$.details").exists())
        .andExpect(jsonPath("$.details.firstPost").value("2015-01-02T12:12:00"))
        .andExpect(jsonPath("$.details.lastPost").value("2020-01-02T12:12:00"))
        .andExpect(jsonPath("$.details.totalPosts").value("100"))
        .andExpect(jsonPath("$.details.totalScore").value("0"))
        .andExpect(jsonPath("$.details.totalAcceptedPosts").value("15"))
        .andExpect(jsonPath("$.details.avgScore").value("5.6"))
        .andDo(MockMvcResultHandlers.print());
  }

  @Test
  void givenInvalidUrlProvidedShouldReturn500() throws Exception {
    // given
    String url = "http://invalidurl.com/resource";
    ImmutableProcessFileRequest processFileRequest = ImmutableProcessFileRequest.of(url);

    // when
    when(fileProcessorService.processFile(url)).thenThrow(FileProcessingException.class);

    mockMvc
        .perform(
            post("/analyze")
                .header("Content-Type", "application/json")
                .content(objectMapper.writeValueAsBytes(processFileRequest)))
        .andDo(print())
        .andExpect(status().isInternalServerError());
  }

  @Test
  void givenInvalidRequestShouldReturn400() throws Exception {
    // given
    String url = "{\"ur\": \"https://s3-eu-west-1.amazonaws.com/test.xml}\"";

    // when
    mockMvc
        .perform(post("/analyze").header("Content-Type", "application/json").content(url))
        .andDo(print())
        .andExpect(status().is4xxClientError());
  }
}
