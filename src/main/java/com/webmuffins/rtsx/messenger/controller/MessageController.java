package com.webmuffins.rtsx.messenger.controller;

import com.webmuffins.rtsx.messenger.model.Message;
import com.webmuffins.rtsx.messenger.model.MessageRequest;
import com.webmuffins.rtsx.messenger.service.MessageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Resource
    MessageService messageService;

    @GetMapping
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    @PostMapping
    public Message createMessages(@Valid @RequestBody MessageRequest messageRequest) {
        return messageService.createNewMessages(messageRequest);
    }
}
