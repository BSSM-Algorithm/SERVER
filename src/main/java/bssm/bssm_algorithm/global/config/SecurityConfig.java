package bssm.bssm_algorithm.global.config;

import bssm.bssm_algorithm.global.security.filter.BssmAlgorithmAuthenticationFilter;
import bssm.bssm_algorithm.global.security.filter.BssmAlgorithmExceptionFilter;
import bssm.bssm_algorithm.global.security.jwt.JwtProvider;
import bssm.bssm_algorithm.global.security.user.BssmAlgorithmUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ObjectMapper objectMapper;
    private final JwtProvider jwtProvider;
    private final BssmAlgorithmUserDetailsService bssmAlgorithmUserDetailsService;
    private final List<String> excludedUrls = List.of("/static/**", "/resources/**", "/public/**", "/auth/login", "/auth/reissue");

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(excludedUrls.toArray(new String[0])).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new BssmAlgorithmAuthenticationFilter(bssmAlgorithmUserDetailsService, jwtProvider, excludedUrls), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new BssmAlgorithmExceptionFilter(objectMapper), BssmAlgorithmAuthenticationFilter.class);

        return http.build();
    }
}
