package com.example.springboothello_world;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@Slf4j
@SpringBootApplication
public class SpringbootHelloWorldApplication {

	public static void main(String[] args) {
	    log.info("args: {}",args);
		SpringApplication.run(SpringbootHelloWorldApplication.class, args);
	}

}
