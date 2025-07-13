package bssm.bssm_algorithm.domain.auth.presentation.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ChangePasswordRequest(
        @NotBlank(message = "새 비밀번호는 필수입니다.")
        @Pattern(regexp = "^[^\\s]+$", message = "비밀번호에는 공백이 포함될 수 없습니다.")
        String password
) {
}
