package com.webmuffins.rtsx.messenger.service.impl;

import com.webmuffins.rtsx.messenger.dto.MessageRequestDto;
import com.webmuffins.rtsx.messenger.dto.MessageResponseDto;
import com.webmuffins.rtsx.messenger.entity.Message;
import com.webmuffins.rtsx.messenger.exception.MessageNotFoundException;
import com.webmuffins.rtsx.messenger.mapper.Mapper;
import com.webmuffins.rtsx.messenger.repository.MessageRepository;
import com.webmuffins.rtsx.messenger.service.MessageService;
import com.webmuffins.rtsx.messenger.util.DateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
                .collect(Collectors.toList());
    }

    private int compareDate(MessageResponseDto lhs, MessageResponseDto rhs) {
        if (lhs.getCreationDate() == null) {
            return -1;
        }

        if (rhs.getCreationDate() == null) {
            return 1;
        }

        LocalDateTime lhsCreationDate = DateUtil.stringIntoDate(lhs.getCreationDate(), dateTimeFormat);
        LocalDateTime rhsCreationDate = DateUtil.stringIntoDate(rhs.getCreationDate(), dateTimeFormat);
        return lhsCreationDate.compareTo(rhsCreationDate);
    }

    @Override
    public MessageResponseDto createNewMessages(MessageRequestDto messageRequestDto) {
        Message message = messageMapper.mapDtoToEntity(messageRequestDto);
        Message insertedMessage = messageRepository.insert(message);
        return messageMapper.mapEntityToDto(insertedMessage);
    }

    @Override
    public MessageResponseDto updateMessage(String id, MessageRequestDto messageRequestDto) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new MessageNotFoundException(String.format("Message with id '%s' not found", id)));
        message.setMessageText(messageRequestDto.getMessageText());

        Message updatedMessage = messageRepository.save(message);
        return messageMapper.mapEntityToDto(updatedMessage);
    }

    @Override
    public void deleteMessage(String id) {
        messageRepository.deleteById(id);
    }
}
