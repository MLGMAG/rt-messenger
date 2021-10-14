package com.webmuffins.rtsx.messenger.service.impl;

import com.webmuffins.rtsx.messenger.converter.Converter;
import com.webmuffins.rtsx.messenger.model.Message;
import com.webmuffins.rtsx.messenger.model.MessageRequest;
import com.webmuffins.rtsx.messenger.repository.MessageRepository;
import com.webmuffins.rtsx.messenger.service.MessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("messageService")
public class MessageServiceImpl implements MessageService {

    @Resource
    private MessageRepository messageRepository;
    @Resource
    private Converter<MessageRequest, Message> messageRequestMessageConverter;

    @Override
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @Override
    public Message createNewMessages(MessageRequest messageRequest) {
        Message message = messageRequestMessageConverter.convert(messageRequest);
        return messageRepository.insert(message);
    }
}
