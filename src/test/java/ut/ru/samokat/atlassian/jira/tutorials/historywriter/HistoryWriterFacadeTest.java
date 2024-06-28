package ut.ru.samokat.atlassian.jira.tutorials.historywriter;

import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.IssueManager;
import com.atlassian.jira.issue.MutableIssue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ru.samokat.atlassian.jira.tutorials.historywriter.HistoryWriterFacade;
import ru.samokat.atlassian.jira.tutorials.historywriter.repository.LogMessageRepository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class HistoryWriterFacadeTest {
    @Mock
    private LogMessageRepository logMessageRepository;
    @Mock
    private IssueManager issueManager;
    @Mock
    private MutableIssue HAPPY_PATH_ISSUE;
    @InjectMocks
    private HistoryWriterFacade historyWriter;
    private final String HAPPY_PATH_ISSUE_KEY = "PRSD-1";

    @BeforeEach
    public void prepare() {
        initMocks(this);
        when(issueManager.getIssueByCurrentKey(HAPPY_PATH_ISSUE_KEY)).thenReturn(HAPPY_PATH_ISSUE);
        when(HAPPY_PATH_ISSUE.getKey()).thenReturn(HAPPY_PATH_ISSUE_KEY);
    }

    @Test
    public void shouldReturnTrueIfIssueExistAndMessageNotNull() {
        //given
        String message = "non null string";
        String issueKey = HAPPY_PATH_ISSUE_KEY;

        //when
        boolean success = historyWriter.writeLogMessageToIssueHistory(issueKey, message);

        //then
        assertTrue(success);
    }

    @Test
    public void shouldFailWhenNullKey() {
        //given
        String message = "non null string";
        String issueKey = null;

        //when
        boolean success = historyWriter.writeLogMessageToIssueHistory(issueKey, message);

        //then
        assertFalse(success);
    }

    @Test
    public void shouldFailWhenNullIssue() {
        //given
        String message = "non null string";
        Issue issue = null;

        //when
        boolean success = historyWriter.writeLogMessageToIssueHistory(issue, message);

        //then
        assertFalse(success);
    }

    @Test
    public void shouldFailWhenMessageNull() {
        //given
        String message = null;
        String issueKey = HAPPY_PATH_ISSUE_KEY;

        //when
        boolean success = historyWriter.writeLogMessageToIssueHistory(issueKey, message);

        //then
        assertFalse(success);
    }

    @Test
    public void shouldFailWhenIssueNotExist() {
        //given
        String message = "non null string";
        String issueKey = HAPPY_PATH_ISSUE_KEY + "_NOT_EXIST";

        //when
        boolean success = historyWriter.writeLogMessageToIssueHistory(issueKey, message);

        //then
        assertFalse(success);
    }
}
