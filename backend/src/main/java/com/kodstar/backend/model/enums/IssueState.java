package com.kodstar.backend.model.enums;

import lombok.Getter;

@Getter
public enum IssueState {

  OPEN,
  CLOSED;

  public static IssueState fromString(String status) {
    for (IssueState issueState : IssueState.values()) {
      if (issueState.name().equalsIgnoreCase(status)) {
        return issueState;
      }
    }
    return OPEN;
  }
}
