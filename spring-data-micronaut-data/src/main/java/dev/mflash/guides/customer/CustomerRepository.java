package dev.mflash.guides.customer;

import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@JdbcRepository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {

  @Override
  Set<Customer> findAll();

  default Optional<Customer> persist(Customer customer) {
    Customer saved = findById(customer.getCustomerId()).get();
    BeanUtils.copyProperties(customer, saved, getNullPropertyNames(customer));
    update(saved.getCustomerId(), saved.getFirstName(), saved.getLastName());
    return findById(customer.getCustomerId());
  }

  @Query("UPDATE Customer SET first_name = :firstName, last_name = :lastName WHERE customer_id = :id")
  void update(long id, String firstName, String lastName);

  static String[] getNullPropertyNames (Object source) {
    final BeanWrapper src = new BeanWrapperImpl(source);
    PropertyDescriptor[] pds = src.getPropertyDescriptors();

    Set<String> emptyNames = new HashSet<>();
    for(PropertyDescriptor pd : pds) {
      Object srcValue = src.getPropertyValue(pd.getName());
      if (srcValue == null) emptyNames.add(pd.getName());
    }

    String[] result = new String[emptyNames.size()];
    return emptyNames.toArray(result);
  }

  Set<Customer> findByLastName(String lastName);

  Set<Customer> findByFirstName(String firstName);

  Set<Customer> findByFirstNameAndLastName(String firstName, String lastName);
}
