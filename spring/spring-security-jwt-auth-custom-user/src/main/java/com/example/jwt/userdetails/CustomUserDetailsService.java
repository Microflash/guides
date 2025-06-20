package com.example.jwt.userdetails;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public record CustomUserDetailsService(CustomUserRepository userRepository) implements UserDetailsService {

	private static final PasswordEncoder PASSWORD_ENCODER = PasswordEncoderFactories.createDelegatingPasswordEncoder();

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		CustomUser customUser = this.userRepository.findByUsername(username);
		if (customUser == null) {
			throw new UsernameNotFoundException("username " + username + " is not found");
		}
		return new CustomUserDetails(customUser);
	}

	public void saveUser(CustomUser newUser) {
		this.userRepository.save(new CustomUser(newUser.username(), PASSWORD_ENCODER.encode(newUser.password())));
	}

	record CustomUserDetails(CustomUser customUser) implements UserDetails {

		private static final List<GrantedAuthority> ROLE_USER = Collections
				.unmodifiableList(AuthorityUtils.createAuthorityList("ROLE_USER"));

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return ROLE_USER;
		}

		@Override
		public String getPassword() {
			return customUser.password();
		}

		@Override
		public String getUsername() {
			return customUser.username();
		}
	}
}
