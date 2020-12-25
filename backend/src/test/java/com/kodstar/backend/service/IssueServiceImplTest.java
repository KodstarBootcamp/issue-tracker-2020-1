package com.kodstar.backend.service;

import com.kodstar.backend.model.dto.Issue;
import com.kodstar.backend.model.entity.IssueEntity;
import com.kodstar.backend.repository.IssueRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
class IssueServiceImplTest {

    @Autowired
    private IssueService issueService;

    @MockBean
    private IssueRepository issueRepository;

    @Test
    void saveIssueEntity() {

        Set<String> labelSet = Set.of("story", "bug");
        Issue issue = new Issue(null, "test", "test is important", labelSet);

        IssueEntity issueEntity = issueService.convertToEntity(issue);
        issueEntity.setId(2l);

        doReturn(issueEntity).when(issueRepository).save(any());

        // Execute the service call
        Issue returnedIssue = issueService.saveIssueEntity(issue);

        // Assert the response
        assertNotNull(returnedIssue, "The saved issue should not be null");
        assertEquals(issue.getTitle(), returnedIssue.getTitle(), "The title of the issue and the title of the issueEntity should be same.");
        assertEquals(2, returnedIssue.getId(), "The id of issue should be equal to id of the issueEntity");
    }
}