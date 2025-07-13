package bssm.bssm_algorithm.global.security.jwt;

import bssm.bssm_algorithm.global.security.exception.InvalidJsonWebTokenException;
import bssm.bssm_algorithm.global.properties.JwtProperties;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtProvider {
    private final SecretKey secretKey;
    private final JwtProperties jwtProperties;

    public JwtProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.secretKey = new SecretKeySpec(
                jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm()
        );
    }

    public String getUsername(String token) throws JwtException {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    public String createAccessToken(String username) {
        return createJwt(username, jwtProperties.getAccessExpiration());
    }

    public String createRefreshToken(String username) {
        return createJwt(username, jwtProperties.getRefreshExpiration());
    }

    public String getAccessToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ") || authorizationHeader.length() == 7) {
            throw new InvalidJsonWebTokenException();
        }
        return jwtVerifyAccessToken(authorizationHeader.substring(7));
    }

    private String createJwt(String username, Long expiredMS) {
        return Jwts.builder()
                .claim("category", "token")
                .claim("username", username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMS))
                .signWith(secretKey)
                .compact();
    }

    private String jwtVerifyAccessToken(String accessToken) {
        try {
            String tokenType = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(accessToken).getPayload().get("category", String.class);
            if(!tokenType.equals("token")) throw new InvalidJsonWebTokenException();
            return accessToken;
        }
        catch (JwtException e) {
            throw new InvalidJsonWebTokenException();
        }
    }
}
