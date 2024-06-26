package ru.samokat.atlassian.jira.tutorials.historywriter.entity;

import net.java.ao.Entity;
import net.java.ao.schema.StringLength;
import net.java.ao.schema.Table;

import java.sql.Timestamp;

@Table("log_messages_tab")
public interface LogMessageEntry extends Entity {
    Long getIssueId();
    void setIssueId(Long id);
    @StringLength(StringLength.UNLIMITED)
    String getText();
    @StringLength(StringLength.UNLIMITED)
    void setText(String text);
    Timestamp getTime();
    void setTime(Timestamp time);

}
