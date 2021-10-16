package com.webmuffins.rtsx.messenger.mapper;

import com.webmuffins.rtsx.messenger.dto.MessageRequestDto;
import com.webmuffins.rtsx.messenger.entity.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class MessageMapper implements Mapper<Message, MessageRequestDto, Message> {

    @Value("${message.creationDate.pattern}")
    private String dateTimeFormat;

    @Override
    public Message mapEntityToDto(Message message) {
        return message;
    }

    @Override
    public Message mapDtoToEntity(MessageRequestDto messageRequest) {
        Message message = new Message();
        message.setMessageText(messageRequest.getMessageText());
        populateCreationDate(message);
        return message;
    }

    private void populateCreationDate(Message messageObj) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(dateTimeFormat);
        LocalDateTime creationDate = LocalDateTime.now();
        String formattedCreationDate = creationDate.format(dateFormat);
        messageObj.setCreationDate(formattedCreationDate);
    }

    public void setDateTimeFormat(String dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
    }
}
