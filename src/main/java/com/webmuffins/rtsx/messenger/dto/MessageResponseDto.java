package com.webmuffins.rtsx.messenger.dto;

public class MessageResponseDto {
    private String messageText;
    private String creationDate;

    public MessageResponseDto() {
    }

    public MessageResponseDto(String messageText, String creationDate) {
        this.messageText = messageText;
        this.creationDate = creationDate;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
