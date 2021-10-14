package com.webmuffins.rtsx.messenger.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class MessageRequest {
    @NotNull
    @NotBlank
    private String messageText;
}
