package com.kodstar.backend.security.jwt;

import com.kodstar.backend.security.userdetails.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Component
public class JwtUtils {

	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	@Autowired
	JwtUtils jwtUtils;

	@Value("${app.jwt.secretKey}")
	private String secretKey;

	@Value("${app.jwt.tokenExpirationDays}")
	private int tokenExpirationDays;

	public String generateJwtToken(Authentication authentication) {

		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

		return Jwts.builder()
				.setSubject((userPrincipal.getUsername()))
				.setIssuedAt(new Date())
				.setExpiration(java.sql.Date.valueOf(LocalDate.now()
						.plusDays(tokenExpirationDays)))
				.signWith(SignatureAlgorithm.HS512, secretKey)
				.compact();
	}

	public String getUserNameFromJwtToken(String token) {

		// TODO: avoid deprecated codes
		/*
		Jwts.parserBuilder().build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
		 */
		return Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser()
					.setSigningKey(secretKey)
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
