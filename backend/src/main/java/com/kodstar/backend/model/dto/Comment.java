package com.kodstar.backend.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

   private Long id;
   private String content;
   private Long issueId;

   @JsonProperty(access = JsonProperty.Access.READ_ONLY)
   private String commentedBy;
}
