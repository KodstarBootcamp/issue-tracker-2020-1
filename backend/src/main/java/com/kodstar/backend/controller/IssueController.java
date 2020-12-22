package com.kodstar.backend.controller;

import com.kodstar.backend.model.dto.Issue;
import com.kodstar.backend.service.IssueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping("/issues")
    public ResponseEntity<Collection<Issue>> getIssues(){
        var issues = issueService.getAllIssues();

        if (issues.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(issues);
    }
}
