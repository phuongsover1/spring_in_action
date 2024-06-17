package com.example.chapter14workingwithrsocketserver.rsocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;

@Configuration
@Slf4j
public class RSocketClientConfiguration {
    @Bean
    public ApplicationRunner sender(RSocketRequester.Builder requestBuilder) {
        return args -> {

            // Handling request-response messaging
            RSocketRequester tcp = requestBuilder.tcp("localhost", 7000);
//
//            tcp.route("greeting")
//                    .data("Hello RSocket!")
//                    .retrieveMono(String.class)
//                    .subscribe(response -> log.info("Got a response: {}", response));
//
//
//            String who = "Phuong";
//            tcp.route("greeting/{name}", who)
//                    .data("Hello RSocket!")
//                    .retrieveMono(String.class)
//                    .subscribe(response -> log.info("Got a response: {}", response));
//
//
            // Handling request-stream messaging
//            String stockSymbol = "XYZ";
//
//            tcp.route("stock/{symbol}", stockSymbol)
//                    .retrieveFlux(StockQuote.class)
//                    .doOnNext(stockQuote -> log.info(
//                            "Price of {} : {} (at {})",
//                            stockQuote.getSymbol(),
//                            stockQuote.getPrice(),
//                            stockQuote.getTimestamp()
//                    ))
//                    .take(10)
//                    .subscribe();
//
//            // Sending fire-and-forget messages
//
//            tcp.route("alert")
//                    .data(new Alert(
//                            Alert.Level.RED, "Phuong", Instant.now()
//                    ))
//                    .send()
//                    .subscribe();
//            log.info("Alert sent");
            // Sending messages bidirectionally

            Flux<GratuityIn> gratuityInFlux =
                    Flux.fromArray(new GratuityIn[]{
                                    new GratuityIn(BigDecimal.valueOf(35.50), 18),
                                    new GratuityIn(BigDecimal.valueOf(10.00), 15),
                                    new GratuityIn(BigDecimal.valueOf(23.25), 20),
                                    new GratuityIn(BigDecimal.valueOf(52.75), 18),
                                    new GratuityIn(BigDecimal.valueOf(80.00), 15),
                            })
                            .delayElements(Duration.ofSeconds(1));

            tcp
                    .route("gratuity")
                    .data(gratuityInFlux)
                    .retrieveFlux(GratuityOut.class)
                    .subscribe(out -> log.info(out.getPercent() + "% gratuity on "
                            + out.getBillTotal() + " is "
                            + out.getGratuity()));
        };

    }
}
