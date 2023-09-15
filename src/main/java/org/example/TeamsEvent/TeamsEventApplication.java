package org.example.TeamsEvent;

import org.example.TeamsEvent.service.MongoConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = "org.example.TeamsEvent")
@Import(MongoConfig.class)
public class TeamsEventApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeamsEventApplication.class, args);
	}

}
