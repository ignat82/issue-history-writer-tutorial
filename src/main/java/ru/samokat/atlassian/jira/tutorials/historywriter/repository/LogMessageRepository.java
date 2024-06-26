package ru.samokat.atlassian.jira.tutorials.historywriter.repository;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import net.java.ao.Query;
import ru.samokat.atlassian.jira.tutorials.historywriter.entity.LogMessageEntry;

import javax.inject.Named;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@Named
public class LogMessageRepository {
    private final ActiveObjects activeObjects;

    public LogMessageRepository(@ComponentImport ActiveObjects activeObjects) {
        this.activeObjects = activeObjects;
    }
    public List<LogMessageEntry> getLogMessageEntries(Long issueId) {
        return Arrays.asList(activeObjects.find(LogMessageEntry.class,
                                                Query.select().where("ISSUE_ID = ?", issueId).order("ID")));
    }

    public void createLogMessage(Long issueId, String message) {
        LogMessageEntry logMessage = activeObjects.create(LogMessageEntry.class);
        logMessage.setIssueId(issueId);
        logMessage.setText(message);
        logMessage.setTime(new Timestamp(System.currentTimeMillis()));
        logMessage.save();
    }
}
