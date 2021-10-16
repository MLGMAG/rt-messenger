package com.webmuffins.rtsx.messenger.controller;

import com.webmuffins.rtsx.messenger.dto.MessageRequestDto;
import com.webmuffins.rtsx.messenger.entity.Message;
import com.webmuffins.rtsx.messenger.service.MessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
public class MessageSocketController {

    private final MessageService messageService;

    public MessageSocketController(MessageService messageService) {
        this.messageService = messageService;
    }

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public Message createMessage(@Valid @RequestBody MessageRequestDto messageRequestDto) {
        return messageService.createNewMessages(messageRequestDto);
    }
}
