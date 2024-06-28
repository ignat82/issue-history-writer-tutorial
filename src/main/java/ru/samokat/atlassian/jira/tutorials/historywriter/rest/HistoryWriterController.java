package ru.samokat.atlassian.jira.tutorials.historywriter.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.samokat.atlassian.jira.tutorials.historywriter.api.HistoryWriter;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Slf4j
@RequiredArgsConstructor
@Path("/")
public class HistoryWriterController {
    private final HistoryWriter historyWriter;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/write")
    public Response writeMessageToIssueLogMessagesTab(HistoryWriterRequest request) {
        log.debug("writeMessageToIssueLogMessagesTab()");

        boolean success = historyWriter.writeLogMessageToIssueHistory(request.issueKey, request.message);

        return success
                ? Response.ok().entity("success").build()
                : Response.status(555).entity("no success((").build();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HistoryWriterRequest {
        private String issueKey;
        private String message;
    }
}
