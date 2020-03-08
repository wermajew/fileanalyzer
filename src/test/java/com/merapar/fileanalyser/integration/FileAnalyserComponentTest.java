package com.merapar.fileanalyser.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.merapar.fileanalyser.request.ImmutableProcessFileRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockserver.client.MockServerClient;
import org.mockserver.model.Header;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.when;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FileAnalyserComponentTest {

  @LocalServerPort private int port;

  private MockServerClient client;

  final ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  void setUp() {
    client = startClientAndServer(8089);
  }

  @Test
  void givenValidXmlInRequestShouldReturnStatusCodeOkAndFileDetails() throws IOException {
    // given
    File file = new File(getClass().getClassLoader().getResource("posts.xml").getFile());
    String xmlResponse = new String(Files.readAllBytes(Paths.get(file.getPath())));
    String url = "http://localhost:8089/test.xml";
    ImmutableProcessFileRequest processFileRequest = ImmutableProcessFileRequest.of(url);

    client
        .when(
            request()
                .withMethod("POST")
                .withPath("/test.xml")
                .withHeader("Content-Type", "application/json")
                .withBody(objectMapper.writeValueAsString(processFileRequest)))
        .respond(
            response()
                .withStatusCode(200)
                .withHeaders(
                    new Header("Content-Type", "text/xml; charset=utf-8"),
                    new Header("Cache-Control", "public, max-age=86400"))
                .withBody(xmlResponse)
                .withDelay(TimeUnit.SECONDS, 1));

    // when
    when()
        .post(String.format("http://localhost:%s/analyze", port))
        .then()
        .assertThat()
        .statusCode(200);
  }

  @AfterAll
  void tearDown() {
    client.stop();
  }
}
