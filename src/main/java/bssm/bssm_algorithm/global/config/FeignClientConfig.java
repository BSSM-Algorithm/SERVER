package bssm.bssm_algorithm.global.config;

import bssm.bssm_algorithm.global.feign.FeignClientErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "bssm.bssm_algorithm")
public class FeignClientConfig {
    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignClientErrorDecoder();
    }
}