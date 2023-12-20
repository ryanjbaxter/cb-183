package org.example.cbsample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;

@SpringBootApplication
public class CbsampleApplication implements CommandLineRunner {

	@Autowired
	private ReactiveCircuitBreakerFactory circuitBreakerFactory;

	public static void main(String[] args) {
		SpringApplication.run(CbsampleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		circuitBreakerFactory.create("demo").run(
			reactor.netty.http.client.HttpClient.create().get()
				.uri("https://httpbin.org/get").
				responseSingle((response, bytes) -> bytes.asString())).subscribe(System.out::println);

	}
}
