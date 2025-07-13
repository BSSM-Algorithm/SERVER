package bssm.bssm_algorithm.global.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {
    String code;
    String message;
}