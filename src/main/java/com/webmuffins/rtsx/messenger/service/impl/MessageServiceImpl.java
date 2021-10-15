package com.webmuffins.rtsx.messenger.service.impl;

import com.webmuffins.rtsx.messenger.dto.MessageRequestDto;
import com.webmuffins.rtsx.messenger.entity.Message;
import com.webmuffins.rtsx.messenger.mapper.Mapper;
import com.webmuffins.rtsx.messenger.repository.MessageRepository;
import com.webmuffins.rtsx.messenger.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("messageService")
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final Mapper<Message, MessageRequestDto, Message> messageMapper;

    public MessageServiceImpl(MessageRepository messageRepository, Mapper<Message, MessageRequestDto, Message> messageMapper) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
    }

    @Override
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @Override
    public Message createNewMessages(MessageRequestDto messageRequestDto) {
        Message message = messageMapper.mapDtoToEntity(messageRequestDto);
        return messageRepository.insert(message);
    }
}
