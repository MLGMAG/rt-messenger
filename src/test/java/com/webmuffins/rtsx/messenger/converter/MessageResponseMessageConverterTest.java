package com.webmuffins.rtsx.messenger.converter;

import com.webmuffins.rtsx.messenger.model.Message;
import com.webmuffins.rtsx.messenger.model.MessageRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MessageResponseMessageConverterTest {

    private static final String MESSAGE_TEXT = "text";
    private static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss.SSS";

    private final MessageResponseMessageConverter testInstance = new MessageResponseMessageConverter();

    private final MessageRequest messageRequest = new MessageRequest();

    @BeforeEach
    void setUp() {
        messageRequest.setMessageText(MESSAGE_TEXT);
        testInstance.setDateTimeFormat(DATE_TIME_FORMAT);
    }

    @Test
    void shouldConvert() {
        Message actual = testInstance.convert(messageRequest);
        assertThat(actual.getMessageText()).isEqualTo(MESSAGE_TEXT);
    }
}
