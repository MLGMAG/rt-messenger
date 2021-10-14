package com.webmuffins.rtsx.messenger.service;

import com.webmuffins.rtsx.messenger.model.Message;
import com.webmuffins.rtsx.messenger.model.MessageRequest;

import java.util.List;

public interface MessageService {
    List<Message> getAllMessages();

    Message createNewMessages(MessageRequest messageRequest);
}
