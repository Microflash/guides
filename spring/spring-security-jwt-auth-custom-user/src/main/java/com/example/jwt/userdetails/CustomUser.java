package com.example.jwt.userdetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@JsonIgnoreProperties(value = { "id" })
public record CustomUser(@Id UUID id, String username, @JsonProperty(access = Access.WRITE_ONLY) String password) {

	public CustomUser(String username, String password) {
		this(null, username, password);
	}
}
