package com.kodstar.backend.service;

import com.kodstar.backend.model.dto.Issue;
import com.kodstar.backend.model.entity.IssueEntity;
import com.kodstar.backend.model.entity.LabelEntity;
import com.kodstar.backend.repository.IssueRepository;
import com.kodstar.backend.repository.LabelRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
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

    @MockBean
    private LabelRepository labelRepository;

    @Test
    void saveIssueEntity() {

        Set<String> labelSet = Set.of("story", "bug");
        Issue issue = new Issue(null, "test", "test is important", labelSet);

        IssueEntity issueEntity = issueService.convertToEntity(issue);
        issueEntity.setId(2L);

        doReturn(issueEntity).when(issueRepository).save(any());

        // Execute the service call
        Issue returnedIssue = issueService.saveIssueEntity(issue);

        // Assert the response
        assertNotNull(returnedIssue, "The saved issue should not be null");
        assertEquals(issue.getTitle(), returnedIssue.getTitle(), "The title of the issue and the title of the issueEntity should be same.");
        assertEquals(2, returnedIssue.getId(), "The id of issue should be equal to id of the issueEntity");
    }

    @Test
    @DisplayName("Test findById Success")
    void testFindById() {
        // Setup our mock repository
        Set<String> labelSet = Set.of("story", "bug");
        Issue issue = new Issue(null, "test", "test is important", labelSet);

        IssueEntity issueEntity = issueService.convertToEntity(issue);
        issueEntity.setId(1L);

        doReturn(Optional.of(issueEntity)).when(issueRepository).findById(1L);

        // Execute the service call
        Issue returnedIssue = issueService.findById(1L);

        // Assert the response
        assertEquals(issueEntity.getId(),returnedIssue.getId());
        assertEquals(issueEntity.getTitle(),returnedIssue.getTitle());
    }

    @Test
    @DisplayName("Test getAllIssues")
    void testFindAll() {
        // Setup our mock repository
        Set<String> labelSet = Set.of("story", "bug");
        Issue issue = new Issue(null, "test", "test is important", labelSet);

        IssueEntity issueEntity1 = issueService.convertToEntity(issue);
        issueEntity1.setId(1L);
        IssueEntity issueEntity2 = issueService.convertToEntity(issue);
        issueEntity2.setId(2L);

        doReturn(Arrays.asList(issueEntity1, issueEntity2)).when(issueRepository).findAll();

        // Execute the service call
        Collection<Issue> issues = issueService.getAllIssues();

        // Assert the response
        assertEquals(2, issues.size(), "getAllIssues should return 2 issues");
    }

    @Test
    @DisplayName("Test getAlllabels")
    void testGetAllLabels() {
        // Setup our mock repository
        LabelEntity labelEntity1 = new LabelEntity();
        labelEntity1.setId(1L);
        labelEntity1.setName("bug");

        doReturn(Arrays.asList(labelEntity1)).when(labelRepository).findAll();

        // Execute the service call
        Collection<String> labels = issueService.getAllLabels();

        // Assert the response
        assertEquals(1, labels.size(), "getAlllabels should return 1 issues");
        assertTrue(labels.contains(labelEntity1.getName()));
    }





}