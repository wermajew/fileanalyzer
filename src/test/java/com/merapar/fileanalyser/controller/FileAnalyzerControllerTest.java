// package com.merapar.fileanalyser.controller;
//
// import com.merapar.fileanalyser.response.FileDetails;
// import com.merapar.fileanalyser.service.FileProcessorService;
// import org.junit.jupiter.api.Test;
// import org.junit.runner.RunWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.test.context.junit4.SpringRunner;
// import org.springframework.test.web.servlet.MockMvc;
//
// import java.math.BigInteger;
// import java.time.LocalDateTime;
// import java.util.Optional;
//
// import static org.mockito.Mockito.when;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
// @RunWith(SpringRunner.class)
// @SpringBootTest
// @AutoConfigureMockMvc
// class FileAnalyzerControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private FileProcessorService fileProcessorService;
//
//    @Test
//    void test() throws Exception {
//        //given
//        String file = "https://s3-eu-west-1.amazonaws.com/test.xml";
//        FileDetails fileDetails = new FileDetails();
//        fileDetails.setTotalPosts(BigInteger.valueOf(12));
//        fileDetails.setTotalAcceptedPosts(BigInteger.valueOf(13));
//        fileDetails.setAvgScore(12.0);
//        fileDetails.setLastPost(LocalDateTime.MIN);
//        fileDetails.setFirstPost(LocalDateTime.MIN);
//        // when
//        when(fileProcessorService.readFile(file)).thenReturn(Optional.of(fileDetails));
//        //then
//        mockMvc.perform(post("/analyze").content("{url\": " + file + "}"))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
// }
