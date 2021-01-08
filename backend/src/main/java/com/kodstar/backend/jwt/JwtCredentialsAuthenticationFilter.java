package com.kodstar.backend.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodstar.backend.model.entity.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

@RequiredArgsConstructor
public class JwtCredentialsAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtConfiguration jwtConfiguration;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
                                                throws AuthenticationException {
        try {
            UserEntity userEntity = new ObjectMapper()
                    .readValue(request.getInputStream(), UserEntity.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
            userEntity.getUsername(), userEntity.getPassword());

            Authentication authenticate = authenticationManager.authenticate(authentication);

            return  authenticate;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities",authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now()
                        .plusDays(jwtConfiguration.getExpirationDays())))
                .signWith(Keys.hmacShaKeyFor(jwtConfiguration.getSecretKey().getBytes()))
                .compact();


        response.addHeader(jwtConfiguration.getAuthorizationHeader(),jwtConfiguration.getTokenPrefix() + token);
    }
}
