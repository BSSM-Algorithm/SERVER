package bssm.bssm_algorithm.global.exception;

import org.springframework.http.HttpStatus;

public class BssmAlgorithmBusinessException extends BssmAlgorithmException {
    public BssmAlgorithmBusinessException(String message, HttpStatus status) {
        super(message, status);
    }
}
