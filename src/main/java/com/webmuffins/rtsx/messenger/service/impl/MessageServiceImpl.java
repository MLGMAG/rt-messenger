package com.webmuffins.rtsx.messenger.service.impl;

import com.webmuffins.rtsx.messenger.dto.MessageRequestDto;
import com.webmuffins.rtsx.messenger.dto.MessageResponseDto;
import com.webmuffins.rtsx.messenger.entity.Message;
import com.webmuffins.rtsx.messenger.mapper.Mapper;
import com.webmuffins.rtsx.messenger.repository.MessageRepository;
import com.webmuffins.rtsx.messenger.service.MessageService;
import com.webmuffins.rtsx.messenger.util.DateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service("messageService")
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final Mapper<Message, MessageRequestDto, MessageResponseDto> messageMapper;

    @Value("${message.creationDate.pattern}")
    private String dateTimeFormat;

    public MessageServiceImpl(MessageRepository messageRepository, Mapper<Message, MessageRequestDto, MessageResponseDto> messageMapper) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
    }

    @Override
    public List<MessageResponseDto> getAllMessages() {
        return messageRepository.findAll().stream()
                .map(messageMapper::mapEntityToDto)
                .sorted(this::compareDate)
                .sorted(Comparator.comparing(MessageResponseDto::getCreationDate))
                .collect(Collectors.toList());
    }

    private int compareDate(MessageResponseDto lhs, MessageResponseDto rhs) {
        LocalDateTime lhsCreationDate = DateUtil.stringIntoDate(lhs.getCreationDate(), dateTimeFormat);
        LocalDateTime rhsCreationDate = DateUtil.stringIntoDate(rhs.getCreationDate(), dateTimeFormat);
        return lhsCreationDate.compareTo(rhsCreationDate);
    }

    @Override
    public Message createNewMessages(MessageRequestDto messageRequestDto) {
        Message message = messageMapper.mapDtoToEntity(messageRequestDto);
        return messageRepository.insert(message);
    }

    @Override
    public void deleteMessage(String id) {
        messageRepository.deleteById(id);
    }
}
