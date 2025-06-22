package org.example.dsproject.restapi.api;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "org.example.dsproject.restapi")
@EnableJpaRepositories(basePackages = "org.example.dsproject.restapi.repository")
@EntityScan(basePackages = "org.example.dsproject.restapi.model")
public class EnergyApiApplication {
 public static void main(String[] args) {
        SpringApplication.run(EnergyApiApplication.class, args);
    }
}
