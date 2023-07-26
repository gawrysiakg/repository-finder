package com.repositoryfinder.finder;

import feign.codec.Decoder;
import feign.jackson.JacksonDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    Decoder feignDecoder(){
        return new JacksonDecoder();
    }


    //Dotyczy to Spring Boot w wersji 3 :
    //
    //Domyślnym typem zawartości używanym przez Feign jest application/x-www-form-urlencoded,
    // dlatego widzisz to zachowanie. Aby zmienić Content-Type na application/json;charset=UTF-8,
    // możesz skonfigurować Feign do używania innego kodera.
    //
    //Skonfiguruj Feign, aby używał JacksonEncoder:
    //
    //@Configuration
    //public class FeignConfig {
    //    @Bean
    //    public Encoder feignEncoder() {
    //        return new JacksonEncoder();
    //    }

}
