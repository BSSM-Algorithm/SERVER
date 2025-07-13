package bssm.bssm_algorithm.global.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "redis")
public class RedisProperties {
    private final int port;
    private final String host;

    public RedisProperties(int port, String host) {
        this.port = port;
        this.host = host;
    }
}
