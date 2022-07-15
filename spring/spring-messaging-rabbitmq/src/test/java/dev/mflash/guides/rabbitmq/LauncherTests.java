package dev.mflash.guides.rabbitmq;

import dev.mflash.guides.rabbitmq.configuration.RabbitMQConfiguration;
import dev.mflash.guides.rabbitmq.domain.Book;
import dev.mflash.guides.rabbitmq.domain.Book.BookBuilder;
import dev.mflash.guides.rabbitmq.service.Reader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@ExtendWith(SpringExtension.class)
public @SpringBootTest class LauncherTests {

  private @Autowired RabbitTemplate rabbitTemplate;
  private @Autowired Reader reader;
  private List<Book> books = new ArrayList<>();

  public @BeforeEach void setUp() {
    books.add(new BookBuilder().isbn("978-1426333064").title("The Falcon's Feather")
        .author("Trudi Trueit").build());
    books.add(new BookBuilder().isbn("978-1785658389").title("Iron City").author("Pat Cadigan")
        .build());
  }

  public @Test void test() throws InterruptedException {
    rabbitTemplate.convertAndSend(RabbitMQConfiguration.TOPIC_EXCHANGE_NAME, "books.new", books);
    reader.getLatch().await(10, TimeUnit.SECONDS);
  }
}
