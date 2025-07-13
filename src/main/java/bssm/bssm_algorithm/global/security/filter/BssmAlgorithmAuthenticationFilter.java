package bssm.bssm_algorithm.global.security.filter;

import bssm.bssm_algorithm.global.security.jwt.JwtProvider;
import bssm.bssm_algorithm.global.security.user.BssmAlgorithmUserDetails;
import bssm.bssm_algorithm.global.security.user.BssmAlgorithmUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class BssmAlgorithmAuthenticationFilter extends OncePerRequestFilter {

    private final BssmAlgorithmUserDetailsService bssmAlgorithmUserDetailsService;
    private final JwtProvider jwtProvider;
    private final List<String> excludedUrls;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtProvider.getAccessToken(request);
        String username = jwtProvider.getUsername(accessToken);

        BssmAlgorithmUserDetails bssmAlgorithmUserDetails = bssmAlgorithmUserDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(bssmAlgorithmUserDetails, null, bssmAlgorithmUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        return excludedUrls.stream()
                .anyMatch(pattern ->
                        new AntPathMatcher().match(pattern, request.getServletPath()));
    }
}
