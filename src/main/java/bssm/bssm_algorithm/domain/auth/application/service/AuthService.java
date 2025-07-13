package bssm.bssm_algorithm.domain.auth.application.service;

import bssm.bssm_algorithm.domain.auth.application.service.exception.PasswordMismatchException;
import bssm.bssm_algorithm.domain.auth.presentation.dto.req.ChangePasswordRequest;
import bssm.bssm_algorithm.domain.auth.presentation.dto.req.LoginRequest;
import bssm.bssm_algorithm.domain.auth.presentation.dto.res.LoginResult;
import bssm.bssm_algorithm.domain.user.domain.entity.UserEntity;
import bssm.bssm_algorithm.domain.user.domain.exception.UsernameNotFoundException;
import bssm.bssm_algorithm.domain.user.domain.repository.UserRepository;
import bssm.bssm_algorithm.global.security.jwt.CookieManager;
import bssm.bssm_algorithm.global.security.jwt.JwtProvider;
import bssm.bssm_algorithm.global.security.user.BssmAlgorithmUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtProvider jwtProvider;
    private final CookieManager cookieManager;

    @Transactional
    public void changePassword(BssmAlgorithmUserDetails bssmAlgorithmUserDetails, ChangePasswordRequest changePasswordRequest) {
        String username = bssmAlgorithmUserDetails.getUsername();

        String newEncodedPassword = bCryptPasswordEncoder.encode(changePasswordRequest.password());

        userRepository.updatePasswordByUsername(newEncodedPassword, username);
    }

    @Transactional
    public LoginResult login(LoginRequest loginRequest) {

        UserEntity userEntity = userRepository.findByUsername(loginRequest.username()).orElseThrow(() -> new UsernameNotFoundException(loginRequest.username()));

        if(!bCryptPasswordEncoder.matches(loginRequest.password(), userEntity.getPassword())) {
            throw new PasswordMismatchException();
        }

        String accessToken = jwtProvider.createAccessToken(loginRequest.username());
        String refreshToken = jwtProvider.createRefreshToken(loginRequest.username());

        String refreshTokenCookie = cookieManager.createRefreshTokenCookie(loginRequest.username(), refreshToken);

        return new LoginResult(accessToken, refreshTokenCookie);
    }

    @Transactional
    public String logout(String username, HttpServletRequest request) {
        cookieManager.checkRefreshToken(username, request);
        return cookieManager.deleteRefreshToken(username);
    }

    @Transactional
    public String reissue(String username, HttpServletRequest request) {
        cookieManager.checkRefreshToken(username, request);
        return jwtProvider.createAccessToken(username);
    }
}
