package bssm.bssm_algorithm.global.exception.entity;

import bssm.bssm_algorithm.global.exception.BssmAlgorithmBusinessException;
import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends BssmAlgorithmBusinessException {
    public EntityNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
