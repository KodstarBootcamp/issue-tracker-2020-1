package com.kodstar.backend.controller;


import com.kodstar.backend.model.dto.Label;
import com.kodstar.backend.service.IssueService;
import com.kodstar.backend.service.LabelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping
@RequiredArgsConstructor
@CrossOrigin(origins = {"*"})
public class LabelController {

    private final LabelService labelService;
    private final IssueService issueService;

    @PostMapping("/label")
    public ResponseEntity<Label> createLabel(@Valid @RequestBody Label label){

        return new ResponseEntity(labelService.saveLabelEntity(label), HttpStatus.CREATED);
    }

}
