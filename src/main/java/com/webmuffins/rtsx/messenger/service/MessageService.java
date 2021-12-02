package com.webmuffins.rtsx.messenger.service;

import com.webmuffins.rtsx.messenger.dto.MessageRequestDto;
import com.webmuffins.rtsx.messenger.dto.MessageResponseDto;
import com.webmuffins.rtsx.messenger.entity.Message;

import java.util.List;

public interface MessageService {
    List<MessageResponseDto> getAllMessages();

    Message createNewMessages(MessageRequestDto messageRequestDto);
}
