package com.kodstar.backend.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class BatchRequest {

    private String method;
    private Set<Long> ids;
}
