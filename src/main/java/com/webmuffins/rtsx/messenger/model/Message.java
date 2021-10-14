package com.webmuffins.rtsx.messenger.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Message {

    @Id
    private String id;
    private String messageText;
    private String creationDate;

}
