package ru.samokat.atlassian.jira.tutorials.historywriter.tabpanel;

import com.atlassian.jira.plugin.issuetabpanel.AbstractIssueAction;
import com.atlassian.jira.plugin.issuetabpanel.IssueTabPanelModuleDescriptor;
import lombok.Getter;
import ru.samokat.atlassian.jira.tutorials.historywriter.entity.LogMessageEntry;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class LogMessageIssueAction extends AbstractIssueAction {
    private final Date timePerformed;
    private final String message;
    @Getter
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    public LogMessageIssueAction(IssueTabPanelModuleDescriptor descriptor,
                                 LogMessageEntry logMessageEntry) {
        super(descriptor);
        this.timePerformed = new Date(logMessageEntry.getTime().getTime());
        this.message = logMessageEntry.getText();
    }

    @Override
    public Date getTimePerformed() {
        return timePerformed;
    }

    @Override
    protected void populateVelocityParams(Map map) {
        map.put("message", message);
        map.put("time", getDateFormat().format(timePerformed));
    }
}
