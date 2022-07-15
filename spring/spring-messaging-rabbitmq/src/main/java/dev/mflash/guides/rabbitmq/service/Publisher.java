package dev.mflash.guides.rabbitmq.service;

import dev.mflash.guides.rabbitmq.configuration.RabbitMQConfiguration;
import dev.mflash.guides.rabbitmq.domain.Book;
import dev.mflash.guides.rabbitmq.domain.Book.BookBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public @Service class Publisher implements CommandLineRunner {

  private final RabbitTemplate rabbitTemplate;
  private final Reader reader;

  public Publisher(RabbitTemplate rabbitTemplate, Reader reader) {
    this.rabbitTemplate = rabbitTemplate;
    this.reader = reader;
  }

  public @Override void run(String... args) throws Exception {
    rabbitTemplate
        .convertAndSend(RabbitMQConfiguration.TOPIC_EXCHANGE_NAME, "books.new", getBooks());
    reader.getLatch().await(10, TimeUnit.SECONDS);
  }

  private List<Book> getBooks() {
    List<Book> books = new ArrayList<>();
    books.add(new BookBuilder().isbn("978-1250078308").title("Archenemies").author("Marissa Meyer")
        .build());
    books.add(new BookBuilder().isbn("978-0399555770").title("Skyward").author("Brandon Sanderson")
        .build());
    books.add(new BookBuilder().isbn("978-0374285067").title("Void Star").author("Zachary Mason")
        .build());

    return books;
  }
}
