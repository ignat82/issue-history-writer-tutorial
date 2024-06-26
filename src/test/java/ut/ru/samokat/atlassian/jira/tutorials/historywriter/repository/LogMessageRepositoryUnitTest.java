package ut.ru.samokat.atlassian.jira.tutorials.historywriter.repository;

import com.atlassian.activeobjects.external.ActiveObjects;
import net.java.ao.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import ru.samokat.atlassian.jira.tutorials.historywriter.entity.LogMessageEntry;
import ru.samokat.atlassian.jira.tutorials.historywriter.repository.LogMessageRepository;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class LogMessageRepositoryUnitTest {
    @Mock
    private ActiveObjects activeObjects;
    @Mock
    private LogMessageEntry emptyLogMessageEntry;
    @InjectMocks
    private LogMessageRepository logMessageRepository;
    private final static Long ISSUE_ID = 1L;

    @BeforeEach
    public void prepare() {
        initMocks(this);
    }

    @Test
    public void shouldReturnListOfEntries() {
        //GIVEN
        LogMessageEntry entry1 = createLogMessageEntryMock(ISSUE_ID, "Test message 1", new Timestamp(System.currentTimeMillis()));
        LogMessageEntry entry2 = createLogMessageEntryMock(ISSUE_ID, "Test message 2", new Timestamp(System.currentTimeMillis()));

        when(activeObjects.find(eq(LogMessageEntry.class),
                                any(Query.class)))
                .thenReturn(new LogMessageEntry[]{entry1, entry2});

        //WHEN
        List<LogMessageEntry> entries = logMessageRepository.getLogMessageEntries(ISSUE_ID);

        //THEN
        assertEquals(2, entries.size());
        assertEquals("Test message 1", entries.get(0).getText());
        assertEquals("Test message 2", entries.get(1).getText());
    }

    private boolean isExpectedQuery(Query query) {
        return query.getWhereClause().equals("ISSUE_ID = ?") && query.getOrderClause().equals("ID");
    }

    @Test
    public void shouldCreateNewLogMessageEntry() {
        //GIVEN
        when(activeObjects.create(LogMessageEntry.class)).thenReturn(emptyLogMessageEntry);

        ArgumentCaptor<Long> issueIdCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<String> textCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Timestamp> timeCaptor = ArgumentCaptor.forClass(Timestamp.class);

        //WHEN
        logMessageRepository.createLogMessage(ISSUE_ID, "Test message 3");

        //THEN
        verify(emptyLogMessageEntry).setIssueId(issueIdCaptor.capture());
        verify(emptyLogMessageEntry).setText(textCaptor.capture());
        verify(emptyLogMessageEntry).setTime(timeCaptor.capture());
        verify(emptyLogMessageEntry).save();

        assertEquals(Long.valueOf(1L), issueIdCaptor.getValue());
        assertEquals("Test message 3", textCaptor.getValue());
        assertTrue(timeCaptor.getValue().getTime() <= System.currentTimeMillis());

    }

    private LogMessageEntry createLogMessageEntryMock(Long issueId, String text, Timestamp time) {
        LogMessageEntry entry = mock(LogMessageEntry.class);
        when(entry.getIssueId()).thenReturn(issueId);
        when(entry.getText()).thenReturn(text);
        when(entry.getTime()).thenReturn(time);
        return entry;
    }

}
