package bssm.bssm_algorithm.global.feign.exception;

import bssm.bssm_algorithm.global.exception.BssmAlgorithmBusinessException;
import org.springframework.http.HttpStatus;

public class FeignClientInvalidRequestException extends BssmAlgorithmBusinessException {
    public FeignClientInvalidRequestException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
