package com.example.jsonserdeexample;

import java.util.UUID;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;

@SpringBootApplication
@EnableBinding(JsonSerdeExampleApplication.KStreamBindings.class)
public class JsonSerdeExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(JsonSerdeExampleApplication.class, args);
	}

	@StreamListener("requesti")
	@SendTo("responseo")
	public KStream<UUID,Account> process(KStream<UUID, Account> events) {
		return events
				.peek((key, value) -> System.out.println("Key: " + key + " account id: " + value.getId() + " account name: " + value.getName()));
	}

	@StreamListener
	public void testOutputFromAboveStreamListener(@Input("testOutput") KStream<UUID, Account> events) {
		events
				.foreach((key, value) -> System.out.println("Testing main SL output: Key: " + key + " account id: " + value.getId() + " account name: " + value.getName()));
	}

	interface KStreamBindings  {
		@Input("requesti")
		KStream<?, ?> input();

		@Output("responseo")
		KStream<?, ?> output();

		@Input("testOutput")
		KStream<?, ?> testOutput();
	}

}
