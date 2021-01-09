package com.kodstar.backend.security.jwt;

import com.google.common.net.HttpHeaders;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "app.jwt")
@NoArgsConstructor
@Data
public class JwtConfiguration {

    private String secretKey;
    private String tokenPrefix;
    private Integer expirationDays;

    public String getAuthorizationHeader() {

        return HttpHeaders.AUTHORIZATION;
    }
}
