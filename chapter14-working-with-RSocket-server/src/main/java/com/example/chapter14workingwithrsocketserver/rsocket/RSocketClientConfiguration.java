package com.example.chapter14workingwithrsocketserver.rsocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;

@Configuration
@Slf4j
public class RSocketClientConfiguration {
    @Bean
    public ApplicationRunner sender(RSocketRequester.Builder requestBuilder) {
        return args ->  {
            RSocketRequester tcp = requestBuilder.tcp("localhost",7000);

            tcp.route("greeting")
                    .data("Hello RSocket!")
                    .retrieveMono(String.class)
                    .subscribe(response -> log.info("Got a response: {}", response));


            String who = "Phuong";
            tcp.route("greeting/{name}", who)
                    .data("Hello RSocket!")
                    .retrieveMono(String.class)
                    .subscribe(response -> log.info("Got a response: {}", response));
        };
    }
}
