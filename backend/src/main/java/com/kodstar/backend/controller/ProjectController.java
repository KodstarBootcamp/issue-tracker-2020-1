package com.kodstar.backend.controller;

import com.kodstar.backend.model.dto.Issue;
import com.kodstar.backend.model.dto.Project;
import com.kodstar.backend.service.IssueService;
import com.kodstar.backend.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping
@RequiredArgsConstructor
@CrossOrigin(origins = {"*"})
public class ProjectController {

  private final ProjectService projectService;

  @Autowired
  private IssueService issueService;

  @GetMapping("/project/{id}")
  public ResponseEntity<Project> getProjectById(@Valid @PathVariable Long id){

    return ResponseEntity.ok(projectService.findById(id));
  }

  @GetMapping("/projects")
  public ResponseEntity<Collection<Project>> getProjects(){

    Collection<Project> projects = projectService.getAllProjects();

    if(projects.isEmpty())
      return ResponseEntity.noContent().build();

    return ResponseEntity.ok(projects);
  }

  @PostMapping("/project")
  public ResponseEntity<Project> createProject(@Valid @RequestBody Project project){

    return new ResponseEntity(projectService.saveProjectEntity(project), HttpStatus.CREATED);

  }

  @PutMapping("/project/{id}")
  public ResponseEntity<Project> updateProject(@Valid @PathVariable Long id, @RequestBody Project project){

    return ResponseEntity.ok(projectService.updateProjectEntity(id,project));

  }

  @DeleteMapping("/project/{id}")
  public ResponseEntity<Void> deleteProject(@Valid @PathVariable Long id){

    projectService.deleteProject(id);

    return ResponseEntity.noContent().build();

  }

  @GetMapping("/project/{id}/issues")
  public ResponseEntity<Collection<Issue>> getIssuesByProjectId(@PathVariable Long id){

    Collection<Issue> projectIssues = issueService.findByProjectId(id);

    if(projectIssues.isEmpty())
      return ResponseEntity.noContent().build();

    return ResponseEntity.ok(projectIssues);
  }

}
