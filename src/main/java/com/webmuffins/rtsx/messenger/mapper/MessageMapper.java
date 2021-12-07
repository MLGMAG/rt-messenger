package com.webmuffins.rtsx.messenger.mapper;

import com.webmuffins.rtsx.messenger.client.UserClient;
import com.webmuffins.rtsx.messenger.constants.Role;
import com.webmuffins.rtsx.messenger.dto.MessageRequestDto;
import com.webmuffins.rtsx.messenger.dto.MessageResponseDto;
import com.webmuffins.rtsx.messenger.entity.Message;
import com.webmuffins.rtsx.messenger.entity.User;
import com.webmuffins.rtsx.messenger.security.JwtTokenProvider;
import com.webmuffins.rtsx.messenger.security.UserPrincipal;
import com.webmuffins.rtsx.messenger.util.DateUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MessageMapper implements Mapper<Message, MessageRequestDto, MessageResponseDto> {

    private static final String FULL_NAME_PATTER = "%s %s";
    private final JwtTokenProvider jwtTokenProvider;
    private final UserClient userClient;
    @Value("${message.creationDate.pattern}")
    private String dateTimeFormat;

    public MessageMapper(JwtTokenProvider jwtTokenProvider, UserClient userClient) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userClient = userClient;
    }

    @Override
    public MessageResponseDto mapEntityToDto(Message message) {
        MessageResponseDto messageResponseDto = new MessageResponseDto();
        messageResponseDto.setId(message.getId());
        messageResponseDto.setMessageText(message.getMessageText());
        messageResponseDto.setCreationDate(message.getCreationDate());
        messageResponseDto.setEmail(message.getEmail());
        messageResponseDto.setFullName(message.getFullName());
        return messageResponseDto;
    }

    @Override
    public Message mapDtoToEntity(MessageRequestDto messageRequest) {
        Message message = new Message();
        message.setMessageText(messageRequest.getMessageText());
        populateUserInfo(message);
        populateCreationDate(message);
        return message;
    }

    private void populateCreationDate(Message message) {
        LocalDateTime creationDate = LocalDateTime.now();
        String formattedCreationDate = DateUtil.dateIntoString(creationDate, dateTimeFormat);
        message.setCreationDate(formattedCreationDate);
    }

    private void populateUserInfo(Message message) {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        populateEmail(message, principal);
        populateFullName(message, principal);
    }

    private void populateEmail(Message message, UserPrincipal principal) {
        message.setEmail(principal.getEmail());
    }

    private void populateFullName(Message message, UserPrincipal principal) {
        String email = principal.getEmail();
        Role role = principal.getRole();
        String jwtToken = jwtTokenProvider.createJwtToken(email, role);

        User user = userClient.findUserByEmail(jwtToken, email);
        String fullName = String.format(FULL_NAME_PATTER, user.getFirstName(), user.getLastName());
        message.setFullName(fullName);
    }
}
