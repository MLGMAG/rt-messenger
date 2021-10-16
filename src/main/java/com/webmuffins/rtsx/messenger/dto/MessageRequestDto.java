package com.webmuffins.rtsx.messenger.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class MessageRequestDto {
    @NotNull
    @NotBlank
    private String messageText;

    public MessageRequestDto() {
    }

    public MessageRequestDto(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
}
