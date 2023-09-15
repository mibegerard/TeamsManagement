package org.example.TeamsEvent.service;

import org.example.TeamsEvent.model.TeamsEventResponse;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeamsEventRepository extends MongoRepository<TeamsEventResponse, String> {
}
