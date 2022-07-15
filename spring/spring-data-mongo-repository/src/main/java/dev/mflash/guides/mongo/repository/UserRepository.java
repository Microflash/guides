package dev.mflash.guides.mongo.repository;

import dev.mflash.guides.mongo.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

}
