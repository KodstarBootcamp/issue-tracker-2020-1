package com.kodstar.backend.model.enums;

import lombok.Getter;

@Getter
public enum IssueState {

  OPEN,
  CLOSED;

  public static IssueState fromString(String state) {
    for (IssueState issueState : IssueState.values()) {
      if (issueState.name().equalsIgnoreCase(state)) {
        return issueState;
      }
    }
    return OPEN;
  }
}
