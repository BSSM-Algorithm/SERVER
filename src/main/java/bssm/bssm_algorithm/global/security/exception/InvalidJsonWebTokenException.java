package bssm.bssm_algorithm.global.security.exception;

import bssm.bssm_algorithm.global.exception.BssmAlgorithmBusinessException;
import org.springframework.http.HttpStatus;

public class InvalidJsonWebTokenException extends BssmAlgorithmBusinessException {
    public InvalidJsonWebTokenException() {
        super("해당 JWT는 검증되지 않았습니다.", HttpStatus.UNAUTHORIZED);
    }
}
