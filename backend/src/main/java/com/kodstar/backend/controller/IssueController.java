package com.kodstar.backend.controller;

import com.kodstar.backend.model.dto.BatchDeleteRequest;
import com.kodstar.backend.model.dto.Issue;
import com.kodstar.backend.model.dto.Label;
import com.kodstar.backend.model.entity.IssueEntity;
import com.kodstar.backend.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;


@RestController
@RequestMapping
@RequiredArgsConstructor
@CrossOrigin(origins = {"*"})
public class IssueController {

    private final IssueService issueService;

    @GetMapping("/issue/{id}")
    public ResponseEntity<Issue> getIssueById(@Valid @PathVariable Long id){

        return ResponseEntity.ok(issueService.findById(id));
    }

    @GetMapping("/issues")
    public ResponseEntity<Collection<Issue>> getIssues(){
        Collection<Issue> issues = issueService.getAllIssues();

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

    @DeleteMapping("/issue/{id}")
    public ResponseEntity<Void> deleteIssue(@Valid @PathVariable Long id){

        issueService.deleteIssue(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/issues/batch")
    public ResponseEntity<Void> deleteMultipleIssues(@Valid @RequestBody BatchDeleteRequest request){

        issueService.deleteMultipleIssues(request);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sortedissues")
    public ResponseEntity<List<Issue>> getAllIssues(@RequestParam(defaultValue = "created,desc") String[] sort) {

        try {
            List<Sort.Order> orders = getOrders(sort);

            Collection<Issue> issues = issueService.findAll(Sort.by(orders));

            if (issues.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity(issues, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private List<Sort.Order> getOrders(String[] sort) {
        List<Sort.Order> orders = new ArrayList<Sort.Order>();

        if (sort[0].contains(",")) {

            for (String sortOrder : sort) {
                String[] sortIssue = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(sortIssue[1]), sortIssue[0]));
            }
        } else {

            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }
        return orders;
    }

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }

        return Sort.Direction.ASC;
    }
}
