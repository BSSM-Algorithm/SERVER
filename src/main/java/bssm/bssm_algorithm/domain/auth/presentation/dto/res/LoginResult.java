package bssm.bssm_algorithm.domain.auth.presentation.dto.res;

public record LoginResult(
        String accessToken,
        String refreshTokenCookie
) {
}
