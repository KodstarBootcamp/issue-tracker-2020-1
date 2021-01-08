package com.kodstar.backend.security;

import com.kodstar.backend.jwt.AuthEntryPointJwt;
import com.kodstar.backend.jwt.JwtConfiguration;
import com.kodstar.backend.jwt.JwtTokenVerifier;
import com.kodstar.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final JwtConfiguration jwtConfiguration;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public JwtTokenVerifier authenticationJwtTokenFilter() {
        return new JwtTokenVerifier();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
/*
        http
                .cors()
                .and()
                .csrf().disable()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //.addFilter(new JwtUsernameAnd)
                //.addFilterAfter()
                .authorizeRequests()
                .antMatchers("/register","/h2")
                    .permitAll()
                .anyRequest()
                .authenticated();

 */

        http
                .cors()
                .and()
                // We don't need CSRF for this example
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                // make sure we use stateless session; session won't be used to
                // store user's state.
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // dont authenticate this particular request
                .authorizeRequests()
                .antMatchers("/auth/**")
                .permitAll()
                .antMatchers("/**")
                .permitAll();
                // all other requests need to be authenticated
                //.anyRequest()
                //.authenticated();

        // Add a filter to validate the tokens with every request
        http.addFilterBefore( authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userService);

        return provider;
    }
}
