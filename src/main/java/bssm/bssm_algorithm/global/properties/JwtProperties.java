package bssm.bssm_algorithm.global.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private final String secret;
    private final Long accessExpiration;
    private final Long refreshExpiration;

    public JwtProperties(String secret, Long accessExpiration, Long refreshExpiration) {
        this.secret = secret;
        this.accessExpiration = accessExpiration;
        this.refreshExpiration = refreshExpiration;
    }

}
