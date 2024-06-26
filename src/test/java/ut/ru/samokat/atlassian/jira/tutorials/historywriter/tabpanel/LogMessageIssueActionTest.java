package ut.ru.samokat.atlassian.jira.tutorials.historywriter.tabpanel;

import com.atlassian.jira.plugin.issuetabpanel.IssueTabPanelModuleDescriptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.samokat.atlassian.jira.tutorials.historywriter.entity.LogMessageEntry;
import ru.samokat.atlassian.jira.tutorials.historywriter.tabpanel.LogMessageIssueAction;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LogMessageIssueActionTest  {
    @Mock private IssueTabPanelModuleDescriptor issueTabPanelModuleDescriptor;
    @Mock private LogMessageEntry logMessageEntry;
    private LogMessageIssueAction logMessageIssueAction;
    private final Date TEST_TIME = new Date();
    private final String TEST_MESSAGE = "test text";
    private final String MESSAGE_PARAMETER_NAME = "message";
    private final String TIME_PARAMETER_NAME = "time";

    @BeforeEach
    public void prepareLogMessageIssueAction() {
        MockitoAnnotations.initMocks(this);

        when(logMessageEntry.getTime()).thenReturn(new Timestamp(TEST_TIME.getTime()));
        when(logMessageEntry.getText()).thenReturn(TEST_MESSAGE);
        logMessageIssueAction = new LogMessageIssueAction(issueTabPanelModuleDescriptor, logMessageEntry);
    }

    @Test
    public void shouldReturnTime() {
        //WHEN
        Date timePerformed = logMessageIssueAction.getTimePerformed();

        //THEN
        assertEquals(TEST_TIME, timePerformed);
    }

    @Test
    public void shouldPopulateMapAsSideEffect() {
        //GIVEN
        ArgumentCaptor<Map> mapCaptor = ArgumentCaptor.forClass(Map.class);


        //WHEN
        logMessageIssueAction.getHtml();

        //THEN
        verify(issueTabPanelModuleDescriptor).getHtml(any(), mapCaptor.capture());
        Map<String, Object> capturedMap = mapCaptor.getValue();
        assertEquals(TEST_MESSAGE, capturedMap.get(MESSAGE_PARAMETER_NAME));
        assertEquals(logMessageIssueAction.getDateFormat().format(TEST_TIME), capturedMap.get(TIME_PARAMETER_NAME));
    }
}
