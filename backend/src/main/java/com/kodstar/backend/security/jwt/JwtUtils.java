package com.kodstar.backend.security.jwt;


import com.kodstar.backend.security.serv.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Component
public class JwtUtils {

	@Autowired
	private JwtConfiguration jwtConfiguration;

	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Autowired
	JwtUtils jwtUtils;

	public String generateJwtToken(Authentication authentication) {

		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

		return Jwts.builder()
				.setSubject((userPrincipal.getUsername()))
				.setIssuedAt(new Date())
				.setExpiration(java.sql.Date.valueOf(LocalDate.now()
						.plusDays(jwtConfiguration.getExpirationDays())))
				.signWith(SignatureAlgorithm.HS512, jwtConfiguration.getSecretKey())
				.compact();
	}

	public String getUserNameFromJwtToken(String token) {

		return Jwts.parser()
				.setSigningKey(jwtConfiguration.getSecretKey())
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser()
					.setSigningKey(jwtConfiguration.getSecretKey())
					.parseClaimsJws(authToken);

			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}
}
