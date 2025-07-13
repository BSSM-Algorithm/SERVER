package bssm.bssm_algorithm.global.security.jwt;

import bssm.bssm_algorithm.domain.refreshtoken.domain.entity.RefreshToken;
import bssm.bssm_algorithm.domain.refreshtoken.domain.repository.RefreshTokenRepository;
import bssm.bssm_algorithm.global.properties.JwtProperties;
import bssm.bssm_algorithm.global.security.exception.InvalidJsonWebTokenException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class CookieManager {
    private final JwtProperties jwtProperties;
    private final RefreshTokenRepository refreshTokenRepository;

    public String deleteRefreshToken(String username) {
        refreshTokenRepository.deleteById(username);
        return createRefreshCookie("", 0L).toString();
    }

    public String createRefreshTokenCookie(String username, String refreshToken) {
        refreshTokenRepository.deleteAllById(Collections.singleton(username));
        refreshTokenRepository.save(
                RefreshToken.builder()
                        .username(username)
                        .refreshToken(refreshToken)
                        .timeToLive(jwtProperties.getRefreshExpiration())
                        .build()
        );
        return createRefreshCookie(refreshToken, jwtProperties.getRefreshExpiration()).toString();
    }

    private ResponseCookie createRefreshCookie(String value, Long maxAge) {
        return ResponseCookie.from("refresh", value)
                .maxAge(maxAge)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .build();
    }

    public void checkRefreshToken(String username, HttpServletRequest request) {
        if (request.getCookies() == null) {
            throw new InvalidJsonWebTokenException();
        }

        RefreshToken refreshTokenObject = refreshTokenRepository.findById(username).orElseThrow(InvalidJsonWebTokenException::new);

        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "refresh".equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(InvalidJsonWebTokenException::new);

        if(!refreshTokenObject.getRefreshToken().equals(refreshToken)) throw new InvalidJsonWebTokenException();
    }
}
