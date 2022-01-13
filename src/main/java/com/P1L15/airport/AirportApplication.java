package com.P1L15.airport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class AirportApplication {

  public static void main(String[] args) {
    SpringApplication.run(AirportApplication.class, args);
  }
}
