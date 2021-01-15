package com.kodstar.backend.controller;

import com.kodstar.backend.model.dto.*;
import com.kodstar.backend.service.*;
import com.kodstar.backend.service.impl.IssueSearchAndSortFilterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "project")
public class ProjectController {

  private final ProjectService projectService;

  private final IssueSearchAndSortFilterService searchAndSortFilterService;

  @Autowired
  private IssueService issueService;

  @GetMapping("/project/{id}")
  @Operation(summary = "Find a project by id")
  public ResponseEntity<Project> getProjectById(@Valid @PathVariable Long id) {

    return ResponseEntity.ok(projectService.findById(id));
  }

  @GetMapping("/projects")
  @Operation(summary = "Find all projects")
  public ResponseEntity<Collection<Project>> getProjects() {

    Collection<Project> projects = projectService.getAllProjects();

    if (projects.isEmpty())
      return ResponseEntity.noContent().build();

    return ResponseEntity.ok(projects);
  }

  @PostMapping("/project")
  @Operation(summary = "Add a new project")
  public ResponseEntity<Project> createProject(@Valid @RequestBody Project project) {

    return new ResponseEntity(projectService.saveProjectEntity(project), HttpStatus.CREATED);

  }

  @PutMapping("/project/{id}")
  @Operation(summary = "Update a project")
  public ResponseEntity<Project> updateProject(@Valid @PathVariable Long id, @RequestBody Project project) {

    return ResponseEntity.ok(projectService.updateProjectEntity(id, project));

  }

  @DeleteMapping("/project/{id}")
  @Operation(summary = "Delete a project")
  public ResponseEntity<Void> deleteProject(@Valid @PathVariable Long id) {

    projectService.deleteProject(id);

    return ResponseEntity.noContent().build();

  }

  @GetMapping("/project/{id}/issues")
  @Operation(summary = "Find all issues of a project")
  public ResponseEntity<Map<String,Object>> getIssuesByProjectId(@PathVariable Long id,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "3") int size) {

    Map<String,Object> response = issueService.findByProjectId(id, page, size);

    if (((List) response.get("issues")).isEmpty())
        return ResponseEntity.noContent().build();

    return ResponseEntity.ok(response);
  }

  @GetMapping("/project/{id}/issues/search")
  public ResponseEntity<Collection<Issue>> filterAndSort(
          @PathVariable Long id,
          @RequestParam(defaultValue = "") String field,
          @RequestParam(defaultValue = "") String key,
          @RequestParam(defaultValue = "newest") String sort) {

    Collection<Issue> response = searchAndSortFilterService.filterAndSort(id, field, key, sort);

    if (response.isEmpty())
      return ResponseEntity.noContent().build();

    return ResponseEntity.ok(response);
  }

}
