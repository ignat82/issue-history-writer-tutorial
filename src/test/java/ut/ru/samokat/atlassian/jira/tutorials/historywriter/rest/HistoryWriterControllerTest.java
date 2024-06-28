package ut.ru.samokat.atlassian.jira.tutorials.historywriter.rest;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.samokat.atlassian.jira.tutorials.historywriter.api.HistoryWriter;
import ru.samokat.atlassian.jira.tutorials.historywriter.rest.HistoryWriterController;
import ru.samokat.atlassian.jira.tutorials.historywriter.rest.HistoryWriterController.HistoryWriterRequest;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.isNull;
import static org.mockito.Mockito.when;

@Slf4j
public class HistoryWriterControllerTest {
    @Mock private HistoryWriter historyWriter;
    @InjectMocks private HistoryWriterController historyWriterController;

    private final HistoryWriterRequest HAPPY_PATH_REQ = new HistoryWriterRequest("PRSD-4709", "test");
    private final HistoryWriterRequest NON_EXISTING_KEY_REQ = new HistoryWriterRequest("KEY-not-exist", "test");
    private final HistoryWriterRequest NULL_KEY_REQ = new HistoryWriterRequest(null, "test");
    private final HistoryWriterRequest NULL_MESSAGE_REQ = new HistoryWriterRequest("PRSD-4709", null);

    @BeforeEach
    public void prepare() {
        MockitoAnnotations.initMocks(this);
        when(historyWriter.writeLogMessageToIssueHistory("PRSD-4709", "test")).thenReturn(true);
        when(historyWriter.writeLogMessageToIssueHistory(ArgumentMatchers.anyString(), isNull())).thenReturn(false);
        when(historyWriter.writeLogMessageToIssueHistory((String) isNull(), ArgumentMatchers.anyString())).thenReturn(false);
    }

    @Test
    public void shouldReturnTrueWhenIssueWithKeyExistsAndMessageAreNotNull() {
        //given
        HistoryWriterRequest request = HAPPY_PATH_REQ;

        //when
        Response response = historyWriterController.writeMessageToIssueLogMessagesTab(request);

        //then
        assertEquals("success", response.getEntity());
        assertEquals(response.getStatus(), 200);
    }

    @Test
    public void shouldReturnErrorWhenNullMessage() {
        //given
        HistoryWriterRequest request = NULL_MESSAGE_REQ;

        //when
        Response response = historyWriterController.writeMessageToIssueLogMessagesTab(request);

        //then
        assertEquals("no success((", response.getEntity());
        assertEquals(response.getStatus(), 555);
    }

    @Test
    public void shouldReturnErrorWhenNullKey() {
        //given
        HistoryWriterRequest request = NULL_KEY_REQ;

        //when
        Response response = historyWriterController.writeMessageToIssueLogMessagesTab(request);

        //then
        assertEquals("no success((", response.getEntity());
        assertEquals(response.getStatus(), 555);
    }

    @Test
    public void shouldReturnErrorWhenNonExistingKey() {
        //given
        HistoryWriterRequest request = NON_EXISTING_KEY_REQ;

        //when
        Response response = historyWriterController.writeMessageToIssueLogMessagesTab(request);

        //then
        assertEquals("no success((", response.getEntity());
        assertEquals(response.getStatus(), 555);
    }

}

