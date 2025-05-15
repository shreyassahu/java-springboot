package com.shreyas.postgres;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BooksApiApplication {

	private static final Logger log =
					LoggerFactory.getLogger(BooksApiApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BooksApiApplication.class, args);
	}
}
