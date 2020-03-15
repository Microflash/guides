package dev.mflash.guides.mongo.configuration;

import dev.mflash.guides.mongo.domain.Account;
import dev.mflash.guides.mongo.domain.User;
import dev.mflash.guides.mongo.domain.User.Builder;
import dev.mflash.guides.mongo.domain.Session;

import java.time.LocalDate;
import java.time.Month;
import java.util.Locale;
import java.util.Map;

public class TestData {

  private TestData() {
  }

  public enum City {
    TORONTO("Toronto"), LOS_ANGELES("Los Angeles"), LONDON("London"),
    PARIS("Paris"), TOKYO("Tokyo");

    public String name;

    City(String name) {
      this.name = name;
    }
  }

  public static final Map<City, Session> sessions = Map.of(
      City.TORONTO, new Session.Builder().city(City.TORONTO.name).locale(Locale.CANADA).build(),
      City.LOS_ANGELES, new Session.Builder().city(City.LOS_ANGELES.name).locale(Locale.US).build(),
      City.LONDON, new Session.Builder().city(City.LONDON.name).locale(Locale.UK).build(),
      City.PARIS, new Session.Builder().city(City.PARIS.name).locale(Locale.FRANCE).build(),
      City.TOKYO, new Session.Builder().city(City.TOKYO.name).locale(Locale.JAPAN).build()
  );

  public enum Name {
    TINA_LAWRENCE("Tina Lawrence"), ADRIAN_CHASE("Adrian Chase"), MOHD_ALI("Mohd Ali");

    public String name;

    Name(String name) {
      this.name = name;
    }
  }

  public static final Map<Name, User> users = Map.of(
      Name.TINA_LAWRENCE,
      new Builder().name(Name.TINA_LAWRENCE.name).locale(Locale.CANADA).dateOfBirth(
          LocalDate.of(1989, Month.JANUARY, 11)).build(),
      Name.ADRIAN_CHASE,
      new Builder().name(Name.ADRIAN_CHASE.name).locale(Locale.UK).dateOfBirth(
          LocalDate.of(1994, Month.APRIL, 23)).build(),
      Name.MOHD_ALI,
      new Builder().name(Name.MOHD_ALI.name).locale(Locale.JAPAN).dateOfBirth(
          LocalDate.of(1999, Month.OCTOBER, 9)).build()
  );

  public enum Address {
    TINA_LAWRENCE("tina@makeshift.com"), ADRIAN_CHASE("adrian@dynasty.com"),
    MOHD_ALI("mdali@finesse.com");

    public String email;

    Address(String email) {
      this.email = email;
    }
  }

  public static final Map<Address, Account> account = Map.of(
      Address.TINA_LAWRENCE, new Account.Builder().user(users.get(Name.TINA_LAWRENCE))
          .address(Address.TINA_LAWRENCE.email).session(sessions.get(City.TORONTO))
          .session(sessions.get(City.LOS_ANGELES)).build(),
      Address.ADRIAN_CHASE, new Account.Builder().user(users.get(Name.ADRIAN_CHASE))
          .address(Address.ADRIAN_CHASE.email).session(sessions.get(City.LOS_ANGELES))
          .session(sessions.get(City.LONDON)).build(),
      Address.MOHD_ALI, new Account.Builder().user(users.get(Name.MOHD_ALI))
          .address(Address.MOHD_ALI.email).session(sessions.get(City.TOKYO))
          .session(sessions.get(City.PARIS)).build()
  );
}
