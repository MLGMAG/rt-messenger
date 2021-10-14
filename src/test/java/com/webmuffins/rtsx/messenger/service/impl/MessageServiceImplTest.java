package com.webmuffins.rtsx.messenger.service.impl;

import com.webmuffins.rtsx.messenger.converter.Converter;
import com.webmuffins.rtsx.messenger.model.Message;
import com.webmuffins.rtsx.messenger.model.MessageRequest;
import com.webmuffins.rtsx.messenger.repository.MessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageServiceImplTest {

    private final MessageRequest messageRequest = new MessageRequest();
    private final Message message = new Message();
    private final List<Message> messageList = Collections.singletonList(message);
    @Mock
    private MessageRepository messageRepository;
    @Mock
    private Converter<MessageRequest, Message> messageRequestMessageConverter;
    @InjectMocks
    private MessageServiceImpl testInstance;

    @Test
    void shouldGetAllMessages() {
        when(messageRepository.findAll()).thenReturn(messageList);

        List<Message> actual = testInstance.getAllMessages();

        assertThat(actual).isEqualTo(messageList);
    }

    @Test
    void shouldCreateNewMessages() {
        when(messageRequestMessageConverter.convert(messageRequest)).thenReturn(message);
        when(messageRepository.insert(message)).thenReturn(message);

        Message actual = testInstance.createNewMessages(messageRequest);

        assertThat(actual).isEqualTo(message);
    }
}
