package com.webmuffins.rtsx.messenger.mapper;

import com.webmuffins.rtsx.messenger.client.UserClient;
import com.webmuffins.rtsx.messenger.constants.Role;
import com.webmuffins.rtsx.messenger.dto.MessageRequestDto;
import com.webmuffins.rtsx.messenger.dto.MessageResponseDto;
import com.webmuffins.rtsx.messenger.entity.Message;
import com.webmuffins.rtsx.messenger.entity.User;
import com.webmuffins.rtsx.messenger.security.JwtTokenProvider;
import com.webmuffins.rtsx.messenger.security.UserPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageMapperTest {

    private static final String MESSAGE_TEXT = "text";
    private static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss.SSS";
    private static final String CREATION_DATE_STRING = "17/07/2001 22:01:22.929";
    private static final String EMAIL = "email";
    private static final Role ROLE = Role.USER;
    private static final String JWT_TOKEN = "jwtToken";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String FULL_NAME = "firstName lastName";

    private final MessageRequestDto messageRequestDto = new MessageRequestDto();
    private final Message message = new Message();

    @Mock
    private Authentication authentication;
    @Mock
    private SecurityContext securityContext;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private UserClient userClient;
    @InjectMocks
    private MessageMapper testInstance;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(testInstance, "dateTimeFormat", DATE_TIME_FORMAT);
    }

    @Test
    void shouldMapDtoToEntity() {
        setupSecurityMocks();
        messageRequestDto.setMessageText(MESSAGE_TEXT);

        Message actual = testInstance.mapDtoToEntity(messageRequestDto);

        assertThat(actual.getMessageText()).isEqualTo(MESSAGE_TEXT);
        assertThat(actual.getCreationDate()).isNotEmpty();
        assertThat(actual.getEmail()).isEqualTo(EMAIL);
        assertThat(actual.getFullName()).isEqualTo(FULL_NAME);
    }

    private void setupSecurityMocks() {
        UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.setEmail(EMAIL);
        userPrincipal.setRole(ROLE);

        User user = new User();
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);

        when(authentication.getPrincipal()).thenReturn(userPrincipal);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        when(jwtTokenProvider.createJwtToken(EMAIL, ROLE)).thenReturn(JWT_TOKEN);
        when(userClient.findUserByEmail(JWT_TOKEN, EMAIL)).thenReturn(user);
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
