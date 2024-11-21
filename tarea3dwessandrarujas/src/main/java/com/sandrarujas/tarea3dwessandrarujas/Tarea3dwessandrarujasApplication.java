package com.sandrarujas.tarea3dwessandrarujas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication 
public class Tarea3dwessandrarujasApplication {
	
	
	//Referencia a clase principal
	@Bean
	public Principal applicationStartupRunner() {
		return new Principal();
	}
	

	public static void main(String[] args) {
		SpringApplication.run(Tarea3dwessandrarujasApplication.class, args);
	}

}
