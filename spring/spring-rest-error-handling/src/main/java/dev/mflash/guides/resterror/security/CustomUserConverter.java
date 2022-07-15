package dev.mflash.guides.resterror.security;

import dev.mflash.guides.resterror.domain.CustomUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class CustomUserConverter {

  public static User toUser(CustomUser user) {
    return new User(user.getEmail(), user.getPassword(), List.of());
  }

  public static UsernamePasswordAuthenticationToken toAuthenticationToken(CustomUser user) {
    return new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), List.of());
  }
}
