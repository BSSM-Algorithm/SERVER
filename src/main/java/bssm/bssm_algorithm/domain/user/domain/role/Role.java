package bssm.bssm_algorithm.domain.user.domain.role;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

    ANONYMOUS("ROLE_ANONYMOUS"),
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String value;
}
