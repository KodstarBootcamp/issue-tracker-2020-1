package com.kodstar.backend.controller;

import com.kodstar.backend.model.dto.Issue;
import com.kodstar.backend.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Collection;


@RestController
@RequestMapping
@RequiredArgsConstructor
public class IssueController {

    private final IssueService issueService;

    @GetMapping("/issue/{id}")
    public ResponseEntity<Issue> getIssueById(@Valid @PathVariable Long id){

        return ResponseEntity.ok(issueService.findById(id));
    }

    @GetMapping("/issues")
    public ResponseEntity<Collection<Issue>> getIssues(){
        var issues = issueService.getAllIssues();

        if (issues.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(issues);
    }

    @PostMapping("/issue")
    public ResponseEntity<Issue> createIssue(@Valid @RequestBody Issue issue){

        return new ResponseEntity(issueService.saveIssueEntity(issue), HttpStatus.CREATED);
    }

    @PutMapping("/issue/{id}")
    public ResponseEntity<Issue> updateIssue(@Valid @PathVariable Long id, @RequestBody Issue issue){

        return ResponseEntity.ok(issueService.updateIssueEntity(id,issue));

    }

    @GetMapping("/issues/labels")
    public ResponseEntity<Collection<String>> getAllLabels(){

        var labels = issueService.getAllLabels();

        return ResponseEntity.ok(labels);
    }
}
