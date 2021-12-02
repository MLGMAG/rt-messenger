package com.webmuffins.rtsx.messenger.service.impl;

import com.webmuffins.rtsx.messenger.dto.MessageRequestDto;
import com.webmuffins.rtsx.messenger.dto.MessageResponseDto;
import com.webmuffins.rtsx.messenger.entity.Message;
import com.webmuffins.rtsx.messenger.mapper.Mapper;
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

    private final MessageRequestDto messageRequestDto = new MessageRequestDto();

    @Mock
    private MessageRepository messageRepository;
    @Mock
    private Mapper<Message, MessageRequestDto, MessageResponseDto> messageMapper;
    @InjectMocks
    private MessageServiceImpl testInstance;

    private final Message message = new Message();
    private final MessageResponseDto messageResponseDto = new MessageResponseDto();

    private final List<Message> messageList = Collections.singletonList(message);
    private final List<MessageResponseDto> messageResponseDtoList = Collections.singletonList(messageResponseDto);

    @Test
    void shouldGetAllMessages() {
        when(messageRepository.findAll()).thenReturn(messageList);
        when(messageMapper.mapEntityToDto(message)).thenReturn(messageResponseDto);

        List<MessageResponseDto> actual = testInstance.getAllMessages();

        assertThat(actual).isEqualTo(messageResponseDtoList);
    }

    @Test
    void shouldCreateNewMessages() {
        when(messageMapper.mapDtoToEntity(messageRequestDto)).thenReturn(message);
        when(messageRepository.insert(message)).thenReturn(message);

        Message actual = testInstance.createNewMessages(messageRequestDto);

        assertThat(actual).isEqualTo(message);
    }
}
