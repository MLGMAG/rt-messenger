package com.webmuffins.rtsx.messenger.controller;

import com.webmuffins.rtsx.messenger.dto.MessageRequestDto;
import com.webmuffins.rtsx.messenger.dto.MessageResponseDto;
import com.webmuffins.rtsx.messenger.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public List<MessageResponseDto> getAllMessages() {
        return messageService.getAllMessages();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDto createMessages(@Valid @RequestBody MessageRequestDto messageRequestDto) {
        return messageService.createNewMessages(messageRequestDto);
    }

    @PutMapping("/{id}")
    public MessageResponseDto updateMessage(@PathVariable String id, @Valid @RequestBody MessageRequestDto messageRequestDto) {
        return messageService.updateMessage(id, messageRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMessage(@PathVariable String id) {
        messageService.deleteMessage(id);
    }
}
