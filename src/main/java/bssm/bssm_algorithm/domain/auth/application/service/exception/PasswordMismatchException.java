package bssm.bssm_algorithm.domain.auth.application.service.exception;

import bssm.bssm_algorithm.global.exception.BssmAlgorithmBusinessException;
import org.springframework.http.HttpStatus;

public class PasswordMismatchException extends BssmAlgorithmBusinessException {
    public PasswordMismatchException() {
        super("비밀번호가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
    }
}
