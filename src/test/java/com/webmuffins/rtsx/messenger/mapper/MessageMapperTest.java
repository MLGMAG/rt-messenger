package com.webmuffins.rtsx.messenger.mapper;

import com.webmuffins.rtsx.messenger.dto.MessageRequestDto;
import com.webmuffins.rtsx.messenger.dto.MessageResponseDto;
import com.webmuffins.rtsx.messenger.entity.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

class MessageMapperTest {

    private static final String MESSAGE_TEXT = "text";
    private static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss.SSS";
    private static final String CREATION_DATE_STRING = "17/07/2001 22:01:22.929";

    private final MessageRequestDto messageRequestDto = new MessageRequestDto();
    private final Message message = new Message();

    private final MessageMapper testInstance = new MessageMapper();

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(testInstance, "dateTimeFormat", DATE_TIME_FORMAT);
    }

    @Test
    void shouldMapDtoToEntity() {
        messageRequestDto.setMessageText(MESSAGE_TEXT);

        Message actual = testInstance.mapDtoToEntity(messageRequestDto);

        assertThat(actual.getMessageText()).isEqualTo(MESSAGE_TEXT);
        assertThat(actual.getCreationDate()).isNotEmpty();
    }

    @Test
    void shouldMapEntityToDto() {
        message.setMessageText(MESSAGE_TEXT);
        message.setCreationDate(CREATION_DATE_STRING);

        MessageResponseDto messageResponseDto = testInstance.mapEntityToDto(message);

        assertThat(messageResponseDto.getMessageText()).isEqualTo(MESSAGE_TEXT);
        assertThat(messageResponseDto.getCreationDate()).isEqualTo(CREATION_DATE_STRING);
    }
}
