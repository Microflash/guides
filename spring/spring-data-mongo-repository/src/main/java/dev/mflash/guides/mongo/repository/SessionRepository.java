package dev.mflash.guides.mongo.repository;

import dev.mflash.guides.mongo.domain.Session;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SessionRepository extends MongoRepository<Session, String> {

}
