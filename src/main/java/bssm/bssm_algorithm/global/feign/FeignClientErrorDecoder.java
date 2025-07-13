package bssm.bssm_algorithm.global.feign;

import bssm.bssm_algorithm.global.feign.exception.FeignClientInvalidRequestException;
import bssm.bssm_algorithm.global.feign.exception.FeignClientResponseException;
import bssm.bssm_algorithm.global.feign.exception.FeignClientUnauthorizedException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
public class FeignClientErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        if (response.status() == 401) {
            throw new FeignClientUnauthorizedException("인증되지 않은 접근이 일어났습니다.", HttpStatus.UNAUTHORIZED);
        }
        else if(response.status() >= 400 && response.status() < 500) {
            throw new FeignClientInvalidRequestException(
                    response.reason(),
                    HttpStatus.BAD_REQUEST
            );
        }
        else {
            throw new FeignClientResponseException(
                    "FeignClient의 요청 에러코드 : " + response.status(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
