package bssm.bssm_algorithm.global.security.filter;

import bssm.bssm_algorithm.global.exception.ErrorResponse;
import bssm.bssm_algorithm.global.security.exception.InvalidJsonWebTokenException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.http.entity.ContentType;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class BssmAlgorithmExceptionFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        }
        catch(JwtException e) {
            handleJwtException(response, e);
        }
        catch(InvalidJsonWebTokenException e) {
            handleInvalidJsonWebTokenException(response, e);
        }
    }

    private void handleJwtException(HttpServletResponse response, JwtException jwtException) throws IOException {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(jwtException.getMessage())
                .build();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType(ContentType.APPLICATION_JSON.toString());
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

    private void handleInvalidJsonWebTokenException(HttpServletResponse response, InvalidJsonWebTokenException invalidJsonWebTokenException) throws IOException {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(invalidJsonWebTokenException.getMessage())
                .build();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType(ContentType.APPLICATION_JSON.toString());
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
