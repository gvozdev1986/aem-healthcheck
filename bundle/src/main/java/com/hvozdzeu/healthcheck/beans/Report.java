package com.hvozdzeu.healthcheck.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Report {

    private String name;
    private String status;
    private Long elapsedTimeInMs;
    private Metadata metadata;
    private List<Messages> messages;
    private String messagesUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getElapsedTimeInMs() {
        return elapsedTimeInMs;
    }

    public void setElapsedTimeInMs(Long elapsedTimeInMs) {
        this.elapsedTimeInMs = elapsedTimeInMs;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public List<Messages> getMessages() {
        return messages;
    }

    public void setMessages(List<Messages> messages) {
        this.messages = messages;
    }

    public String getMessagesUrl() {
        return messagesUrl;
    }

    public void setMessagesUrl(String messagesUrl) {
        this.messagesUrl = messagesUrl;
    }

    @Override
    public String toString() {
        return "Report{" +
            "name='" + name + '\'' +
            ", status='" + status + '\'' +
            ", elapsedTimeInMs=" + elapsedTimeInMs +
            ", metadata=" + metadata +
            ", messages=" + messages +
            ", messagesUrl='" + messagesUrl + '\'' +
            '}';
    }
}
