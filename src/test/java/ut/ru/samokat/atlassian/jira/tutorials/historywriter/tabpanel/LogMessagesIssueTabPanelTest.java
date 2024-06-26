package ut.ru.samokat.atlassian.jira.tutorials.historywriter.tabpanel;

import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.user.ApplicationUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.samokat.atlassian.jira.tutorials.historywriter.entity.LogMessageEntry;
import ru.samokat.atlassian.jira.tutorials.historywriter.repository.LogMessageRepository;
import ru.samokat.atlassian.jira.tutorials.historywriter.tabpanel.LogMessagesIssueTabPanel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LogMessagesIssueTabPanelTest {
    @Mock private Issue issueWithActions;

    @Mock private Issue issueWithNoActions;

    @Mock private ApplicationUser user;

    @Mock private LogMessageRepository logMessageRepository;

    @InjectMocks private LogMessagesIssueTabPanel logMessagesIssueTabPanel;

    @BeforeEach
    public void prepareLogMessageRepository() {
        MockitoAnnotations.initMocks(this);
        when(issueWithActions.getId()).thenReturn(1L);
        when(issueWithNoActions.getId()).thenReturn(2L);
    }

    @Test
    public void shouldReturnListOfIssueAction() {

        //GIVEN
        LogMessageEntry firstEntry = mock(LogMessageEntry.class);
        when(firstEntry.getText()).thenReturn("firstEntry text");
        when(firstEntry.getTime()).thenReturn(new Timestamp(new Date().getTime()));
        LogMessageEntry secondEntry = mock(LogMessageEntry.class);
        when(secondEntry.getText()).thenReturn("secondEntry text");
        when(secondEntry.getTime()).thenReturn(new Timestamp(new Date().getTime()));
        List<LogMessageEntry> logMessageEntries = Arrays.asList(firstEntry, secondEntry);

        //WHEN
        when(logMessageRepository.getLogMessageEntries(issueWithActions.getId())).thenReturn(logMessageEntries);

        //THEN
        assertEquals(2, logMessagesIssueTabPanel.getActions(issueWithActions, user).size());
    }

    @Test
    public void shouldReturnEmptyListOfIssueAction() {

        //WHEN
        when(logMessageRepository.getLogMessageEntries(issueWithNoActions.getId())).thenReturn(new ArrayList<>());

        //THEN
        assertEquals(0, logMessagesIssueTabPanel.getActions(issueWithNoActions, user).size());
    }

    @Test
    public void shouldReturnTrueForAnyInput() {
        assertTrue(logMessagesIssueTabPanel.showPanel(issueWithActions, user));
        assertTrue(logMessagesIssueTabPanel.showPanel(issueWithNoActions, user));
        assertTrue(logMessagesIssueTabPanel.showPanel(null, null));
    }
}
