package com.webmuffins.rtsx.messenger.dto;

public class MessageResponseDto {

    private String id;
    private String messageText;
    private String creationDate;
    private String email;
    private String fullName;

    public MessageResponseDto() {
    }

    public MessageResponseDto(String id, String messageText, String creationDate, String email, String fullName) {
        this.id = id;
        this.messageText = messageText;
        this.creationDate = creationDate;
        this.email = email;
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
