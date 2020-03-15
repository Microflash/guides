package dev.mflash.guides.mongo.repository;

import dev.mflash.guides.mongo.domain.Account;
import dev.mflash.guides.mongo.domain.User;
import dev.mflash.guides.mongo.domain.Session;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AccountRepository extends MongoRepository<Account, String> {

  Account findDistinctFirstByUser(User user);

  List<Account> findBySessions(Session session);

  Account findByAddress(String address);
}
