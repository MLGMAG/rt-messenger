package com.webmuffins.rtsx.messenger.dto;

import java.time.LocalDateTime;

public class MessageResponseDto {
    private String messageText;
    private LocalDateTime creationDate;

    public MessageResponseDto() {
    }

    public MessageResponseDto(String messageText, LocalDateTime creationDate) {
        this.messageText = messageText;
        this.creationDate = creationDate;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
