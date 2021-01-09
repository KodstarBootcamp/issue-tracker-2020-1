package com.kodstar.backend.service;

import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class IssueServiceImplTest {

    /*@Autowired
    private IssueService issueService;

    @MockBean
    private IssueRepository issueRepository;

    @MockBean
    private LabelRepository labelRepository;

    @Test
    @DisplayName("Test saveIssue Success")
    void saveIssueEntity() {
        // Setup our mock repository
        Set<String> labelSet = Set.of("story", "bug");
        Issue issue = new Issue(null, "test", "test is important", null, IssueCategory.BACKLOG.name(), IssueState.OPEN.name());

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
    @DisplayName("Test saveIssue Exception")
    void saveIssueException() throws Exception {

        // Setup our mock repository without "issue Title"
        Set<String> labelSet = Set.of("story", "bug");
        Issue issue = new Issue();
        issue.setId(1L);
        issue.setDescription("test is important");
        //issue.setLabels(labelSet);

        // Assert the response
        assertThrows(NullPointerException.class,()->
                issueService.saveIssueEntity(issue));
    }

    @Test
    @DisplayName("Test findById Success")
    void testFindById() {
        // Setup our mock repository
        Set<String> labelSet = Set.of("story", "bug");
        Issue issue = new Issue(null, "test", "test is important", null, IssueCategory.BACKLOG.name(), IssueState.OPEN.name());

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
       // Set<String> labelSet = Set.of("story", "bug");
        Issue issue = new Issue(null, "test", "test is important", null, IssueCategory.BACKLOG.name(), IssueState.OPEN.name());

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
    @DisplayName("Test deleteIssue Success")
    void deleteIssue() throws Exception {

       IssueEntity issueEntity = new IssueEntity();
       issueEntity.setId(1L);
       doReturn(Optional.of(issueEntity)).when(issueRepository).findById(1L);

       issueService.deleteIssue(1L);

       verify(issueRepository, times(1)).delete(issueEntity);

    }

    @Test
    @DisplayName("Test findById Not Found")
    void testFindByIdNotFound() {

        // Setup our mock repository
        doReturn(Optional.empty()).when(issueRepository).findById(1L);

        // Assert the response
        assertThrows(EntityNotFoundException.class,()->issueService.findById(1L));
    }*/

}