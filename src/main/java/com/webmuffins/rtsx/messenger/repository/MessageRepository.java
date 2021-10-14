package com.webmuffins.rtsx.messenger.repository;

import com.webmuffins.rtsx.messenger.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message, String> {
}
