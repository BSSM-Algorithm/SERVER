package bssm.bssm_algorithm.domain.refreshtoken.domain.entity;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash(value = "refreshToken")
@Builder
@Getter
public class RefreshToken {

    @Id
    private String username;

    @Column(unique = true, nullable = false)
    private String refreshToken;

    @TimeToLive
    private Long timeToLive;
}
