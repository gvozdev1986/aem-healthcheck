package com.hvozdzeu.healthcheck.beans;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Report {

    private String name;
    private String status;
    private Long elapsedTimeInMs;
    private Metadata metadata;
    private List<Messages> messages;
    private String messagesUrl;
    private FaIcon faIcon;

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

    public FaIcon getFaIcon() {
        return faIcon;
    }

    public void setFaIcon(FaIcon faIcon) {
        this.faIcon = faIcon;
    }
}
