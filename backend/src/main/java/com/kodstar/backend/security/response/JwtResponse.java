package com.kodstar.backend.security.response;

import lombok.Data;


@Data
public class JwtResponse {
	private String token;
	private Long id;
	private String username;
	private String email;


	public JwtResponse(String token, Long id, String username, String email) {
		this.token = token;
		this.id = id;
		this.username = username;
		this.email = email;
	}
}
