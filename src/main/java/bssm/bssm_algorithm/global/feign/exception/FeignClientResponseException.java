package bssm.bssm_algorithm.global.feign.exception;

import bssm.bssm_algorithm.global.exception.BssmAlgorithmSystemError;
import org.springframework.http.HttpStatus;

public class FeignClientResponseException extends BssmAlgorithmSystemError {
    public FeignClientResponseException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
