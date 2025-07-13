package bssm.bssm_algorithm.global.feign.exception;

import bssm.bssm_algorithm.global.exception.BssmAlgorithmBusinessException;
import org.springframework.http.HttpStatus;

public class FeignClientUnauthorizedException extends BssmAlgorithmBusinessException {
    public FeignClientUnauthorizedException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
