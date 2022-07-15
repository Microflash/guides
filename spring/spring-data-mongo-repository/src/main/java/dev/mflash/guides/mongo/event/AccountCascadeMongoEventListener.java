package dev.mflash.guides.mongo.event;

import dev.mflash.guides.mongo.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.util.ReflectionUtils;

import java.util.Objects;

public class AccountCascadeMongoEventListener extends AbstractMongoEventListener<Account> {

  private @Autowired MongoOperations mongoOperations;
  private Account deletedAccount;

  public @Override void onBeforeSave(BeforeSaveEvent<Account> event) {
    final Object source = event.getSource();
    ReflectionUtils.doWithFields(source.getClass(), new CascadeSaveCallback(source, mongoOperations));
  }

  public @Override void onBeforeDelete(BeforeDeleteEvent<Account> event) {
    final Object id = Objects.requireNonNull(event.getDocument()).get("_id");
    deletedAccount = mongoOperations.findById(id, Account.class);
  }

  public @Override void onAfterDelete(AfterDeleteEvent<Account> event) {
    ReflectionUtils.doWithFields(Account.class, new CascadeDeleteCallback(deletedAccount, mongoOperations));
  }
}
