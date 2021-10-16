package com.webmuffins.rtsx.messenger.service;

import com.webmuffins.rtsx.messenger.dto.MessageRequestDto;
import com.webmuffins.rtsx.messenger.entity.Message;

import java.util.List;

public interface MessageService {
    List<Message> getAllMessages();

    Message createNewMessages(MessageRequestDto messageRequestDto);
}
