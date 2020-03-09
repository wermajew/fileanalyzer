package com.merapar.fileanalyser.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.merapar.fileanalyser.request.ImmutableProcessFileRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
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

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FileAnalyserComponentTest {

  @LocalServerPort private int port;

  private MockServerClient client;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  void setUp() {
    client = startClientAndServer(8089);
    RestAssured.defaultParser = Parser.JSON;
  }

  @Test
  void givenValidXmlInRequestShouldReturnStatusCodeOkAndFileDetails() throws IOException {
    // given
    File file = new File(getClass().getClassLoader().getResource("posts.xml").getFile());
    String xmlResponse = new String(Files.readAllBytes(Paths.get(file.getPath())));
    String url = "http://localhost:8089/test.xml";
    ImmutableProcessFileRequest processFileRequest = ImmutableProcessFileRequest.of(url);

    client
        .when(request().withMethod("GET").withPath("/test.xml"))
        .respond(
            response()
                .withStatusCode(200)
                .withHeaders(
                    new Header("Content-Type", "text/xml; charset=utf-8"),
                    new Header("Cache-Control", "public, max-age=86400"))
                .withBody(xmlResponse)
                .withDelay(TimeUnit.SECONDS, 1));

    // when, then
    given()
        .baseUri(String.format("http://localhost:%s", port))
        .header("Content-Type", "application/json")
        .body(objectMapper.writeValueAsBytes(processFileRequest))
        .contentType(ContentType.JSON)
        .when()
        .post("/analyze")
        .then()
        .log()
        .all()
        .and()
        .statusCode(200)
        .and()
        .body("analyseDate", notNullValue())
        .body("details", notNullValue())
        .body("details.totalPosts", equalTo(8))
        .body("details.totalScore", equalTo(20))
        .body("details.totalAcceptedPosts", equalTo(1))
        .body("details.avgScore", is(2.5f));
  }

  @AfterAll
  void tearDown() {
    client.stop();
  }
}
