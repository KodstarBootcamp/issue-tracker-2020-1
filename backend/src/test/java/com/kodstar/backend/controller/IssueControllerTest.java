package com.kodstar.backend.controller;

import com.kodstar.backend.service.IssueService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
class IssueControllerTest {

    @MockBean
    private IssueService issueService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createIssue() {

    }
}