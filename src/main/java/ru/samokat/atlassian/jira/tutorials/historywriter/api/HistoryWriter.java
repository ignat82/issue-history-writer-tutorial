package ru.samokat.atlassian.jira.tutorials.historywriter.api;

import com.atlassian.jira.issue.Issue;

public interface HistoryWriter {
    boolean writeLogMessageToIssueHistory(Issue issue, String message);
    boolean writeLogMessageToIssueHistory(String issueKey, String message);
}
