package bssm.bssm_algorithm.global.exception;

import org.springframework.http.HttpStatus;

public class BssmAlgorithmSystemError extends BssmAlgorithmException {
    public BssmAlgorithmSystemError(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
