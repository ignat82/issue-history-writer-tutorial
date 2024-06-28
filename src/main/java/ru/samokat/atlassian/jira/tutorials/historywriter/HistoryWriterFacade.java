package ru.samokat.atlassian.jira.tutorials.historywriter;

import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.IssueManager;
import com.atlassian.plugin.spring.scanner.annotation.export.ExportAsService;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import lombok.extern.slf4j.Slf4j;
import ru.samokat.atlassian.jira.tutorials.historywriter.api.HistoryWriter;
import ru.samokat.atlassian.jira.tutorials.historywriter.repository.LogMessageRepository;

import javax.inject.Named;

@Named
@Slf4j
@ExportAsService(HistoryWriter.class)
public class HistoryWriterFacade implements HistoryWriter {
    private final LogMessageRepository logMessageRepository;
    private final IssueManager issueManager;

    public HistoryWriterFacade(LogMessageRepository logMessageRepository,
                               @ComponentImport IssueManager issueManager) {
        this.logMessageRepository = logMessageRepository;
        this.issueManager = issueManager;
    }

    @Override
    public boolean writeLogMessageToIssueHistory(Issue issue, String message) {
        log.debug("writeLogMessageToIssueHistory({}, {})", issue, message);

        if (message == null) {
            log.warn("trying to write NULL message to issue history. do not writing anything to Log Messages issue tab. check where caller takes it from");
            return false;
        }
        if (issue == null) {
            log.warn("issue provided is NULL. do not writing anything to Log Messages issue tab. check where caller takes it from");
            return false;
        }
        log.debug("writeMessageToIssueHistory({}, {})", issue.getKey(), message);
        logMessageRepository.createLogMessage(issue.getId(), message);
        return true;
    }

    @Override
    public boolean writeLogMessageToIssueHistory(String issueKey, String message) {
        log.debug("writeMessageToIssueHistory({}, {})", issueKey, message);

        if (issueKey == null) {
            log.warn("issue key provided is NULL. check where caller takes it from");
            return false;
        }

        Issue issue = issueManager.getIssueByCurrentKey(issueKey);
        if (issue == null) {
            log.warn("failed to pick issue by key {}. do not writing anything to Log Messages issue tab. " +
                             "check where caller takes it from", issueKey);
            return false;
        }

        return writeLogMessageToIssueHistory(issue, message);
    }
}
