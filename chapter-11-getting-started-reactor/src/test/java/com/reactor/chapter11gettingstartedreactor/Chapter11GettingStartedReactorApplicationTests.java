package com.reactor.chapter11gettingstartedreactor;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

@SpringBootTest
class Chapter11GettingStartedReactorApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void createAFlux_just() {
		Flux<String> fruitFlux = Flux.just("Apple", "Orange", "Grape", "Banana", "Strawberry");

		fruitFlux.subscribe(s -> System.out.println("Here's some fruit: " + s));
	}

}
