package com.shreyas.postgres;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

import lombok.extern.java.Log;

@SpringBootApplication
public class PostgresApplication {

	private static final Logger log =
					LoggerFactory.getLogger(PostgresApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PostgresApplication.class, args);
	}
}
