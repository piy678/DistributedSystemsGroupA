package org.example.dsproject.restapi.api;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "org.example.dsproject.restapi")
public class EnergyApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(EnergyApiApplication.class, args);
    }
}
