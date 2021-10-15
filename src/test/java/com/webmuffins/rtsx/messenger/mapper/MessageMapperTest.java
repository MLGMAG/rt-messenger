package com.webmuffins.rtsx.messenger.mapper;

import com.webmuffins.rtsx.messenger.entity.Message;
import com.webmuffins.rtsx.messenger.dto.MessageRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MessageMapperTest {

    private static final String MESSAGE_TEXT = "text";
    private static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss.SSS";

    private final MessageMapper testInstance = new MessageMapper();

    private final MessageRequestDto messageRequestDto = new MessageRequestDto();

    @BeforeEach
    void setUp() {
        messageRequestDto.setMessageText(MESSAGE_TEXT);
        testInstance.setDateTimeFormat(DATE_TIME_FORMAT);
    }

    @Test
    void shouldConvert() {
        Message actual = testInstance.mapDtoToEntity(messageRequestDto);
        assertThat(actual.getMessageText()).isEqualTo(MESSAGE_TEXT);
    }
}
