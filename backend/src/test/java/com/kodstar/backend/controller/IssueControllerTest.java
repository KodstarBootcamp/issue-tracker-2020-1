package com.kodstar.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodstar.backend.model.dto.Issue;
import com.kodstar.backend.service.IssueService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class IssueControllerTest {

    private final static String CONTENT_TYPE = "application/json";

    @MockBean
    private IssueService issueService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Test createIssue Success")
    void createIssue() throws Exception{
        
        // Setup our mock repository
        Set<String> labelSet = Set.of("story", "bug");
        Issue issue = new Issue(1L, "test", "test is important", labelSet);

        ResultActions resultActions = mockMvc.perform(post("/issue")
                .contentType(CONTENT_TYPE)
                .content(objectMapper.writeValueAsString(issue)));

        // Assert the response
        ArgumentCaptor<Issue> captor = ArgumentCaptor.forClass(Issue.class);
        verify(issueService,times(1)).saveIssueEntity(captor.capture());
        assertThat(captor.getValue().getTitle().equals(issue.getTitle()));
        resultActions.andExpect(status().isCreated());

    }


}