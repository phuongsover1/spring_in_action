package com.reactor.chapter11gettingstartedreactor;

import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

@SpringBootTest
class Chapter11GettingStartedReactorApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void createAFlux_just() {
		Flux<String> fruitFlux = Flux.just("Apple", "Orange", "Grape", "Banana", "Strawberry");

//		fruitFlux.subscribe(s -> System.out.println("Here's some fruit: " + s));
		StepVerifier.create(fruitFlux)
				.expectNext("Apple")
				.expectNext("Orange")
				.expectNext("Grape")
				.expectNext("Banana")
				.expectNext("Strawberry")
				.verifyComplete();

	}

	@Test
	void createAFlux_fromArray() {
		String[] fruits = new String[] {"Apple", "Orange", "Grape", "Banana", "Strawberry"};

		Flux<String> fruitFlux = Flux.fromArray(fruits);

		StepVerifier.create(fruitFlux)
				.expectNext("Apple")
				.expectNext("Orange")
				.expectNext("Grape")
				.expectNext("Banana")
				.expectNext("Strawberry")
				.verifyComplete();
	}

	@Test
	void createAFlux_fromIterable() {
		List<String> fruitList = new ArrayList<>();
		fruitList.add("Apple");
		fruitList.add("Orange");
		fruitList.add("Grape");
		fruitList.add("Banana");
		fruitList.add("Strawberry");

		Flux<String> fruitFlux = Flux.fromIterable(fruitList);

		StepVerifier.create(fruitFlux)
				.expectNext("Apple")
				.expectNext("Orange")
				.expectNext("Grape")
				.expectNext("Banana")
				.expectNext("Strawberry")
				.verifyComplete();
	}

	@Test
	void createAFlux_fromStream() {
		Stream<String> fruitStream = Stream.of("Apple", "Orange", "Grape", "Banana", "Strawberry");

		Flux<String> fruitFlux = Flux.fromStream(fruitStream);

		StepVerifier.create(fruitFlux)
				.expectNext("Apple")
				.expectNext("Orange")
				.expectNext("Grape")
				.expectNext("Banana")
				.expectNext("Strawberry")
				.verifyComplete();
	}

	@Test
	void createAFlux_range() {
		Flux<Integer> intervalFlux = Flux.range(1,5);

		StepVerifier.create(intervalFlux)
				.expectNext(1)
				.expectNext(2)
				.expectNext(3)
				.expectNext(4)
				.expectNext(5)
				.verifyComplete();
	}

	@Test
	void createAFlux_interval() {
		Flux<Long> intervalFlux = Flux.interval(Duration.ofSeconds(1)).take(5);

		StepVerifier.create(intervalFlux)
				.expectNext(0L)
				.expectNext(1L)
				.expectNext(2L)
				.expectNext(3L)
				.expectNext(4L)
				.verifyComplete();
	}

	@Test
	void mergeFluxes() {
		Flux<String> characterFlux = Flux.just("Garfield", "Kojak", "Barbossa")
				.delayElements(Duration.ofMillis(500));

		Flux<String> foodFlux = Flux.just("Lasagna", "Lollipops","Apples" )
				.delaySubscription(Duration.ofMillis(250))
				.delayElements(Duration.ofMillis(500));

		Flux<String> mergedFlux = characterFlux.mergeWith(foodFlux).log();

		StepVerifier.create(mergedFlux)
				.expectNext("Garfield")
				.expectNext("Lasagna")
				.expectNext("Kojak")
				.expectNext("Lollipops")
				.expectNext("Barbossa")
				.expectNext("Apples")
				.verifyComplete();

	}

	@Test
	void zipFluxes() {
		Flux<String> characterFlux = Flux.just("Garfield", "Kojak", "Barbossa", "Phuong");

		Flux<String> foodFlux = Flux.just("Lasagna", "Lollipops", "Apples", "PineApple");

		Flux<Tuple2<String, String>> zippedFlux = Flux.zip(characterFlux, foodFlux);

    StepVerifier.create(zippedFlux)
        .expectNextMatches(p ->
            p.getT1().equals("Garfield") && p.getT2().equals("Lasagna"))
        .expectNextMatches(p ->
            p.getT1().equals("Kojak") && p.getT2().equals("Lollipops"))
        .expectNextMatches(p ->
            p.getT1().equals("Barbossa") && p.getT2().equals("Apples"))
        .expectNextMatches(p ->
            p.getT1().equals("Phuong") && p.getT2().equals("PineApple"))
        .verifyComplete();

    zippedFlux.subscribe(System.out::println);
	}

	@Test
	public void zipFluxesToObject() {
		Flux<String> characterFlux = Flux.just("Garfield", "Kojak", "Barbossa");
		Flux<String> foodFlux = Flux.just("Lasagna", "Lollipops", "Apples");

		Flux<String> zippedFlux = Flux.zip(characterFlux, foodFlux, (c,f) -> c + " eats " + f);

		StepVerifier.create(zippedFlux)
				.expectNext("Garfield eats Lasagna")
				.expectNext("Kojak eats Lollipops")
				.expectNext("Barbossa eats Apples")
				.verifyComplete();
	}

	@Test
	public void firstWithSignalFlux() {
		Flux<String> fastFlux = Flux.just("hare", "cheetah", "squirrel");
		Flux<String> slowFlux = Flux.just("tortoise", "snail", "sloth")
						.delaySubscription(Duration.ofMillis(100));


		Flux<String> firstFastestFlux = Flux.firstWithSignal(fastFlux, slowFlux);

		firstFastestFlux.subscribe(System.out::println);
	}

	@Test
	public void skipAFew() {
		Flux<String> countFlux = Flux.just(
				"one", "two", "skip a few", "ninety nine", "one hundred"
		)
				.skip(3);

		StepVerifier.create(countFlux)
				.expectNext("ninety nine", "one hundred")
				.verifyComplete();

	}

	@Test
	public void skipAFewSeconds() {
		Flux<String> countFlux = Flux.just(
				"one", "two", "skip a few", "ninety nine", "one hundred"
		)
				.delayElements(Duration.ofSeconds(1))
				.skip(Duration.ofSeconds(4));

		StepVerifier.create(countFlux)
				.expectNext("ninety nine", "one hundred")
				.verifyComplete();
	}

	@Test
	public void take() {
		Flux<String> countFlux = Flux.just(
				"one", "two", "skip a few", "ninety nine", "one hundred"
		).take(3);

		StepVerifier.create(countFlux)
				.expectNext("one", "two", "skip a few")
				.verifyComplete();

	}

	@Test
	public void takeForAWhile() {

		Flux<String> countFlux = Flux.just(
				"one", "two", "skip a few", "ninety nine", "one hundred"
		)
				.delayElements(Duration.ofSeconds(1))
				.take(Duration.ofSeconds(3));

		StepVerifier.create(countFlux)
				.expectNext("one", "two")
				.verifyComplete();
	}

	@Test
	public void filter() {
		Flux<String> nationalParkFlux = Flux.just(
				"Yellowstone", "Yosemite", "Grand Canyon", "Zion", "Grand Teton"
		)
				.filter(s -> !s.contains(" "));

		StepVerifier.create(nationalParkFlux)
				.expectNext("Yellowstone", "Yosemite", "Zion")
				.verifyComplete();
	}

	@Test
	public void distinct() {
		Flux<String> animalFlux = Flux.just(
				"dog", "cat", "bird", "dog", "bird", "anteater"
		).distinct();

		StepVerifier.create(animalFlux)
				.expectNext("dog", "cat","bird", "anteater")
				.verifyComplete();
	}

	@Test
	public void map() {
		Flux<Player> playerFlux = Flux.just(
				"Michael Jordan", "Scottie Fippen", "Steve Kerr"
		)
				.map(name -> {
					String[] split = name.split("\\s");
					return new Player(split[0], split[1]);
				});

		StepVerifier.create(playerFlux)
				.expectNext(new Player("Michael", "Jordan"))
				.expectNext(new Player("Scottie", "Fippen"))
				.expectNext(new Player("Steve", "Kerr"))
				.verifyComplete();
	}

	@Test
	public void flatMap() {
		Flux<Player> playerFlux = Flux.just(
				"Michael Jordan", "Scottie Pippen", "Steve Kerr"
		)
				.flatMap(n -> Mono.just(n)
						.log()
						.map(p -> {
							String[] split = p.split("\\s");
							return new Player(split[0], split[1]);
						}).subscribeOn(Schedulers.parallel())
				)
				.log();

		List<Player> playerList = Arrays.asList(
				new Player("Michael", "Jordan"),
				new Player("Scottie", "Pippen"),
				new Player("Steve", "Kerr")
		);

		StepVerifier.create(playerFlux)
				.expectNextMatches(p1 -> playerList.contains(p1))
				.expectNextMatches(p2 -> playerList.contains(p2))
				.expectNextMatches(p3 -> playerList.contains(p3))
				.verifyComplete();
	}

	@Test
	public void bufferAndPlatMap() {
		Flux<List<String>> bufferedFlux =  Flux.just(
				"apple", "orange", "kiwi", "strawberry"
		)
				.buffer(3)
				.flatMap(x -> Flux.fromIterable(x)
						.map(String::toUpperCase)
						.log()
						.subscribeOn(Schedulers.parallel()))
				.buffer();

		StepVerifier.create(bufferedFlux)
				.expectNextMatches(list -> list.contains("APPLE") && list.contains("STRAWBERRY") && list.contains("ORANGE") && list.contains("KIWI"))
				.verifyComplete();
	}

	@Test
	public void collectList() {
		Flux<String> fruitFlux = Flux.just(
				"apple", "orange", "banana", "kiwi", "strawberry"
		);

		Mono<List<String>> fruitListMono = fruitFlux.collectList();

		StepVerifier.create(fruitListMono)
				.expectNext(Arrays.asList("apple", "orange", "banana", "kiwi", "strawberry"))
				.verifyComplete();
	}

	@Test
	public void collectMap() {
		Flux<String> animalFlux = Flux.just(
				"aardvark", "elephant", "koala", "eagle", "kangaroo"
		);

		Mono<Map<Character, String>> animapMapMono = animalFlux.collectMap(a -> a.charAt(0));

		StepVerifier.create(animapMapMono)
				.expectNextMatches(map ->
						map.get('a').equals("aardvark") &&
						map.get('e').equals("eagle") &&
						map.get('k').equals("kangaroo"))
				.verifyComplete();
	}

	@Test
	public void all() {
		Flux<String> animalFlux = Flux.just(
				"aardvark", "elephant", "koala", "eagle", "kangaroo"
		);

		Mono<Boolean> hasAMono = animalFlux.all(animal -> animal.contains("a"));

		StepVerifier.create(hasAMono)
				.expectNext(true)
				.verifyComplete();

		Mono<Boolean> hasKMono = animalFlux.all(animal -> animal.contains("k"));

		StepVerifier.create(hasKMono)
				.expectNext(false)
				.verifyComplete();
	}

	@Test
	public void any() {
		Flux<String> animalFlux = Flux.just(
				"aardvark", "elephant", "koala", "eagle", "kangaroo"
		);

		Mono<Boolean> hasGMono = animalFlux.any(animal -> animal.contains("g"));

		StepVerifier.create(hasGMono)
				.expectNext(true)
				.verifyComplete();


		Mono<Boolean> hasZMono = animalFlux.any(animal -> animal.contains("z"));

		StepVerifier.create(hasZMono)
				.expectNext(false)
				.verifyComplete();
	}

	@Data
	private static class Player {
		private final String firstName;
		private final String lastName;
	}

}
