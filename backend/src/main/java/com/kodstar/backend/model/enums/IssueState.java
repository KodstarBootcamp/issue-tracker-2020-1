package com.kodstar.backend.model.enums;

import lombok.Getter;

@Getter
public enum IssueState {

    NEW,
    BACKLOG,
    STARTED,
    REVIEW,
    FINISHED;

    public static IssueState fromString(String str) {
        for (IssueState issueState : IssueState.values()) {
            if (issueState.name().equalsIgnoreCase(str)) {
                return issueState;
            }
        }
        return BACKLOG;
    }
}
