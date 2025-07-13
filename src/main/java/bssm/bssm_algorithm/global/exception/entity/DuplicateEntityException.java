package bssm.bssm_algorithm.global.exception.entity;

import bssm.bssm_algorithm.global.exception.BssmAlgorithmBusinessException;
import org.springframework.http.HttpStatus;

public class DuplicateEntityException extends BssmAlgorithmBusinessException {
    public DuplicateEntityException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
