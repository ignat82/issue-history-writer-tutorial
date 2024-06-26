package ru.samokat.atlassian.jira.tutorials.historywriter.tabpanel;

import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.plugin.issuetabpanel.AbstractIssueTabPanel;
import com.atlassian.jira.plugin.issuetabpanel.IssueAction;
import com.atlassian.jira.plugin.issuetabpanel.IssueTabPanel;
import com.atlassian.jira.user.ApplicationUser;
import lombok.extern.slf4j.Slf4j;
import ru.samokat.atlassian.jira.tutorials.historywriter.entity.LogMessageEntry;
import ru.samokat.atlassian.jira.tutorials.historywriter.repository.LogMessageRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class LogMessagesIssueTabPanel extends AbstractIssueTabPanel implements IssueTabPanel {
    private final LogMessageRepository logMessageRepository;

    public LogMessagesIssueTabPanel(LogMessageRepository logMessageRepository) {
        this.logMessageRepository = logMessageRepository;
    }

    @Override
    public List<IssueAction> getActions(Issue issue, ApplicationUser remoteUser) {
        List<LogMessageEntry> logMessageEntries = logMessageRepository.getLogMessageEntries(issue.getId());
        return logMessageEntries.stream()
                                .map(logMessageEntry -> new LogMessageIssueAction(super.descriptor, logMessageEntry))
                                .collect(Collectors.toList());
    }

    @Override
    public boolean showPanel(Issue issue, ApplicationUser remoteUser) {
        return true;
    }
}
