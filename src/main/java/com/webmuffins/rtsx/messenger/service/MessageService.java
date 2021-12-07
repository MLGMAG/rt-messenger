package com.webmuffins.rtsx.messenger.service;

import com.webmuffins.rtsx.messenger.dto.MessageRequestDto;
import com.webmuffins.rtsx.messenger.dto.MessageResponseDto;

import java.util.List;

public interface MessageService {
    List<MessageResponseDto> getAllMessages();

    MessageResponseDto createNewMessages(MessageRequestDto messageRequestDto);

    MessageResponseDto updateMessage(String id, MessageRequestDto messageRequestDto);

    void deleteMessage(String id);
}
