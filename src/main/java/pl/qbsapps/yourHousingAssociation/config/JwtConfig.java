package pl.qbsapps.yourHousingAssociation.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class JwtConfig {

    @Value("${jwt.header}")
    private String header;

    @Value("${jwt.token.prefix}")
    private String prefix;

    @Value("${jwt.expiration.time}")
    private int expiration;

    @Value("${jwt.secret.key}")
    private String secret;
}
