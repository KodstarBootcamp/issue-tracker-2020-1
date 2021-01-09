package com.kodstar.backend.controller;


import com.kodstar.backend.model.dto.Label;
import com.kodstar.backend.service.IssueService;
import com.kodstar.backend.service.LabelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "label")
public class LabelController {

    private final LabelService labelService;
    private final IssueService issueService;

    @PostMapping("/label")
    @Operation(summary = "Add a new label")
    public ResponseEntity<Label> createLabel(@Valid @RequestBody Label label){

        return new ResponseEntity(labelService.saveLabelEntity(label), HttpStatus.CREATED);
    }

    @GetMapping("/label/{id}")
    @Operation(summary = "Find a label by id")
    public ResponseEntity<Label> getLabelById(@Valid @PathVariable Long id){

        return ResponseEntity.ok(labelService.findById(id));
    }

    @GetMapping("/labels")
    @Operation(summary = "Find all labels")
    public ResponseEntity<Collection<Label>> getLabels(){
        Collection<Label> labels = labelService.getAllLabels();

        if (labels.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(labels);
    }

    @PutMapping("/label/{id}")
    @Operation(summary = "Update a label")
    public ResponseEntity<Label> updateLabel(@Valid @PathVariable Long id, @RequestBody Label label){

        return ResponseEntity.ok(labelService.updateLabelEntity(id, label));
    }

    @DeleteMapping("/label/{id}")
    @Operation(summary = "Delete a label by id")
    public ResponseEntity<Void> deleteLabel(@Valid @PathVariable Long id){
        labelService.deleteLabel(id);

        return ResponseEntity.noContent().build();
    }

}
