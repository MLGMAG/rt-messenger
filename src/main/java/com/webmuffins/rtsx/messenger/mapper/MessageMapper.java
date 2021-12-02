package com.webmuffins.rtsx.messenger.mapper;

import com.webmuffins.rtsx.messenger.dto.MessageRequestDto;
import com.webmuffins.rtsx.messenger.dto.MessageResponseDto;
import com.webmuffins.rtsx.messenger.entity.Message;
import com.webmuffins.rtsx.messenger.util.DateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MessageMapper implements Mapper<Message, MessageRequestDto, MessageResponseDto> {

    @Value("${message.creationDate.pattern}")
    private String dateTimeFormat;

    @Override
    public MessageResponseDto mapEntityToDto(Message message) {
        MessageResponseDto messageResponseDto = new MessageResponseDto();
        messageResponseDto.setMessageText(message.getMessageText());
        populateCreationDate(message, messageResponseDto);
        return messageResponseDto;
    }

    private void populateCreationDate(Message message, MessageResponseDto messageResponseDto) {
        LocalDateTime creationDate = DateUtil.stringIntoDate(message.getCreationDate(), dateTimeFormat);
        messageResponseDto.setCreationDate(creationDate);
    }

    @Override
    public Message mapDtoToEntity(MessageRequestDto messageRequest) {
        Message message = new Message();
        message.setMessageText(messageRequest.getMessageText());
        populateCreationDate(message);
        return message;
    }

    private void populateCreationDate(Message message) {
        LocalDateTime creationDate = LocalDateTime.now();
        String formattedCreationDate = DateUtil.dateIntoString(creationDate, dateTimeFormat);
        message.setCreationDate(formattedCreationDate);
    }
}
