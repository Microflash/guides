package dev.mflash.guides.customer;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@RequestMapping("/customers")
public @RestController class CustomerController {

  private final CustomerRepository repository;

  public CustomerController(CustomerRepository repository) {
    this.repository = repository;
  }

  @GetMapping
  Set<Customer> get(@RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName) {
    Set<Customer> result;

    if (Objects.nonNull(firstName) && Objects.nonNull(lastName)) {
      result = repository.findByFirstNameAndLastName(firstName, lastName);
    } else if (Objects.nonNull(firstName)) {
      result = repository.findByFirstName(firstName);
    } else if (Objects.nonNull(lastName)) {
      result = repository.findByLastName(lastName);
    } else {
      result = repository.findAll();
    }

    return result;
  }

  @PostMapping
  Customer save(Customer customer) {
    return repository.save(customer);
  }

  @PatchMapping
  Optional<Customer> patch(Customer customer) {
    return repository.persist(customer);
  }

  @DeleteMapping("/{id}")
  void delete(@PathVariable("id") @NotBlank Long id) {
    repository.deleteById(id);
  }
}
