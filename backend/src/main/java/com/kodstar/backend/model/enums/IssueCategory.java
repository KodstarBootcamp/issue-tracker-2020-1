package com.kodstar.backend.model.enums;

import lombok.Getter;

@Getter
public enum IssueCategory {

    NEW,
    BACKLOG,
    STARTED,
    REVIEW,
    FINISHED;

    public static IssueCategory fromString(String str) {
        for (IssueCategory issueCategory : IssueCategory.values()) {
            if (issueCategory.name().equalsIgnoreCase(str)) {
                return issueCategory;
            }
        }
        return BACKLOG;
    }
}
