package bssm.bssm_algorithm.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class BssmAlgorithmException extends RuntimeException {

    private final HttpStatus httpStatus;

    public BssmAlgorithmException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
