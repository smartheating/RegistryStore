package de.smartheating.repository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {"de.smartheating.SmartHeatingCommons","de.smartheating.repository"})
@EntityScan(basePackages = {"de.smartheating.SmartHeatingCommons","de.smartheating.repository"})
@EnableJpaRepositories(basePackages = {"de.smartheating.SmartHeatingCommons","de.smartheating.repository"})
@SpringBootApplication
public class RepositoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(RepositoryApplication.class, args);
	}

}
