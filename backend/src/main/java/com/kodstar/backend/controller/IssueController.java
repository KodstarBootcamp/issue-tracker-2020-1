package com.kodstar.backend.controller;

import com.kodstar.backend.model.dto.BatchDeleteRequest;
import com.kodstar.backend.model.dto.Issue;
import com.kodstar.backend.model.dto.User;
import com.kodstar.backend.model.entity.IssueEntity;
import com.kodstar.backend.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Set;


@RestController
@RequestMapping
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;

    @GetMapping("/issue/{id}")
    public ResponseEntity<Issue> getIssueById(@Valid @PathVariable Long id) {

        return ResponseEntity.ok(issueService.findById(id));
    }

    @GetMapping("/issue/user/{userId}")
    public ResponseEntity<Collection<Issue>> getIssuesByUser(@Valid @PathVariable Long userId) {

        return ResponseEntity.ok(issueService.findAllByUser(userId));
    }

    @PostMapping("/issue")
    public ResponseEntity<Issue> createIssue(@Valid @RequestBody Issue issue) {

        return new ResponseEntity(issueService.saveIssueEntity(issue), HttpStatus.CREATED);
    }

    @PutMapping("/issue/{id}")
    public ResponseEntity<Issue> updateIssue(@Valid @PathVariable Long id, @RequestBody Issue issue) {

        return ResponseEntity.ok(issueService.updateIssueEntity(id, issue));

    }

    @PutMapping("/issue/{id}/assignee")
    public ResponseEntity<Issue> assign(@Valid @PathVariable Long id, @RequestBody Set<Long> assignees) {

        return  ResponseEntity.ok(issueService.assignUsersToIssue(id, assignees));
    }

    @DeleteMapping("/issue/{id}")
    public ResponseEntity<Void> deleteIssue(@Valid @PathVariable Long id) {

        issueService.deleteIssue(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/issues/batch")
    public ResponseEntity<Void> deleteMultipleIssues(@Valid @RequestBody BatchDeleteRequest request) {

        issueService.deleteMultipleIssues(request);

        return ResponseEntity.noContent().build();
    }


}
