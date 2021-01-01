package com.kodstar.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodstar.backend.model.dto.Issue;
import com.kodstar.backend.model.dto.Label;
import com.kodstar.backend.repository.IssueRepository;
import com.kodstar.backend.service.IssueService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class IssueControllerTest {

    private Issue issue;

    @MockBean
    private IssueService issueService;

    @MockBean
    private IssueRepository issueRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        Set<String> labelSet = Set.of("story", "bug");
        this.issue = new Issue();
        issue.setId(1L);
        issue.setTitle("test");
        issue.setDescription("test is important");
        issue.setLabels(labelSet);

    }

    @Test
    @DisplayName("Test createIssue Success")
    void createIssue() throws Exception{
        // Setup our mocked service
        when(issueService.saveIssueEntity(any())).thenReturn(issue);

                // Execute the POST request
        mockMvc.perform(post("/issue")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(issue)))

                // Validate the response code and content type
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is(issue.getTitle())))
                .andExpect(jsonPath("$.description", is(issue.getDescription())));

    }

    @Test
    @DisplayName("Test deleteIssue Success")
    void deleteIssue() throws Exception {
        // Setup our mocked service
        doNothing().when(issueService).deleteIssue(1L);

        // Execute the DELETE request
        mockMvc.perform(delete("/issue/{id}",1))
                .andExpect(status().isNoContent());

        verify(issueService, times(1)).deleteIssue(1L);
    }

    @Test
    @DisplayName("Test getIssueById ")
    void testGetIssueById() throws Exception {
                // Setup our mocked service
        when(issueService.findById(1L)).thenReturn(issue);

                // Execute the GET request
        mockMvc.perform(get("/issue/{id}", 1L))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("test")))
                .andExpect(jsonPath("$.description", is("test is important")));
    }

    @Test
    @DisplayName("Test getAllIssues ")
    void testGetAllIssues() throws Exception{
        // Setup our mocked service
        when(issueService.getAllIssues()).thenReturn(Arrays.asList(issue,issue));

        // Execute the GET request
        mockMvc.perform(get("/issues"))

        // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

        // Validate the returned fields
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("test")))
                .andExpect(jsonPath("$[0].description", is("test is important")))
                .andExpect(jsonPath("$[1].id", is(1)))
                .andExpect(jsonPath("$[1].title", is("test")))
                .andExpect(jsonPath("$[1].description", is("test is important")));

    }

    @Test
    @DisplayName("Test getAllIssues_NoContent ")
    void testGetAllIssuesNoContent() throws Exception{
                 // Setup our mocked service
        when(issueService.getAllIssues()).thenReturn(Collections.emptyList());

                 // Execute the GET request
        mockMvc.perform(get("/issues"))

                // Validate the response code
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Test shouldVerifyInvalidIssueId")
    public void shouldVerifyInvalidIssueId() throws Exception {
        // Execute the GET request
         mockMvc.perform(get("/issue/{id}", "abc")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Test shouldVerifyInvalidSaveIssue")
    public void shouldVerifyInvalidSaveIssue() throws Exception {
            // Execute the POST request
        this.mockMvc.perform(post("/issue")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"description\": \"\",\"labels\":[ \"\"]}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test getAllLabels")
    void testGetAllLabels() throws Exception{
                // Setup our mocked service
        Label label1 = new Label();
        label1.setId("1");
        label1.setName("bug");
        label1.setColor("47bd1c");

        Label label2 = new Label();
        label2.setId("1");
        label2.setName("bug");
        label2.setColor("47bd1c");


        Collection<Label> labels = new ArrayList<>();
        labels.add(label1);
        labels.add(label2);

        when(issueService.getAllLabels()).thenReturn(labels);


        // Execute the GET request
        mockMvc.perform(get("/issues/labels"))

                // Validate the response code and content type
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                // Validate the returned fields
                .andExpect(jsonPath("$", hasSize(2)));
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}