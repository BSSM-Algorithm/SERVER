package bssm.bssm_algorithm.domain.auth.presentation.controller;

import bssm.bssm_algorithm.domain.auth.application.service.AuthService;
import bssm.bssm_algorithm.domain.auth.presentation.dto.req.ChangePasswordRequest;
import bssm.bssm_algorithm.domain.auth.presentation.dto.req.LoginRequest;
import bssm.bssm_algorithm.domain.auth.presentation.dto.res.LoginResult;
import bssm.bssm_algorithm.global.security.user.BssmAlgorithmUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PatchMapping("/password")
    public ResponseEntity<String> changePassword(@AuthenticationPrincipal BssmAlgorithmUserDetails bssmAlgorithmUserDetails, @RequestBody @Valid ChangePasswordRequest changePasswordRequest) {
        authService.changePassword(bssmAlgorithmUserDetails, changePasswordRequest);
        return ResponseEntity.ok("비밀번호를 변경하였습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest loginRequest) {
        LoginResult tokens = authService.login(loginRequest);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokens.accessToken())
                .header(HttpHeaders.SET_COOKIE, tokens.refreshTokenCookie())
                .body("로그인에 성공했습니다.");
    }

    @DeleteMapping("/logout")
    public ResponseEntity<String> logout(@AuthenticationPrincipal BssmAlgorithmUserDetails bssmAlgorithmUserDetails, HttpServletRequest request) {
        String refreshTokenCookie = authService.logout(bssmAlgorithmUserDetails.getUsername(), request);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer ")
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie)
                .body("로그아웃에 성공했습니다.");
    }

    @PostMapping("/reissue")
    public ResponseEntity<String> reissue(@AuthenticationPrincipal BssmAlgorithmUserDetails bssmAlgorithmUserDetails, HttpServletRequest request) {
        String accessToken = authService.reissue(bssmAlgorithmUserDetails.getUsername(), request);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .body("로그아웃에 성공했습니다.");
    }
}
