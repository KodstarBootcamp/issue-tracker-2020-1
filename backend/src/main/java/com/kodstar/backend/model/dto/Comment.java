package com.kodstar.backend.model.dto;

import com.kodstar.backend.model.entity.IssueEntity;
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
}
