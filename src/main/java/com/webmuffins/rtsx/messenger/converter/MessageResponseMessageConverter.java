package com.webmuffins.rtsx.messenger.converter;

import com.webmuffins.rtsx.messenger.model.Message;
import com.webmuffins.rtsx.messenger.model.MessageRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class MessageResponseMessageConverter implements Converter<MessageRequest, Message> {

    @Value("${message.creationDate.pattern}")
    private String dateTimeFormat;

    @Override
    public Message convert(MessageRequest messageRequest) {
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
