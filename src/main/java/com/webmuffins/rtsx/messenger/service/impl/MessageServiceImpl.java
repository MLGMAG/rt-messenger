package com.webmuffins.rtsx.messenger.service.impl;

import com.webmuffins.rtsx.messenger.dto.MessageRequestDto;
import com.webmuffins.rtsx.messenger.dto.MessageResponseDto;
import com.webmuffins.rtsx.messenger.entity.Message;
import com.webmuffins.rtsx.messenger.mapper.Mapper;
import com.webmuffins.rtsx.messenger.repository.MessageRepository;
import com.webmuffins.rtsx.messenger.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service("messageService")
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final Mapper<Message, MessageRequestDto, MessageResponseDto> messageMapper;

    public MessageServiceImpl(MessageRepository messageRepository, Mapper<Message, MessageRequestDto, MessageResponseDto> messageMapper) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
    }

    @Override
    public List<MessageResponseDto> getAllMessages() {
        return messageRepository.findAll().stream()
                .map(messageMapper::mapEntityToDto)
                .sorted(Comparator.comparing(MessageResponseDto::getCreationDate))
                .collect(Collectors.toList());
    }

    @Override
    public Message createNewMessages(MessageRequestDto messageRequestDto) {
        Message message = messageMapper.mapDtoToEntity(messageRequestDto);
        return messageRepository.insert(message);
    }
}
