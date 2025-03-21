package com.pdiSpring.cadastroAutor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CadastroAutorApplication {

	public static void main(String[] args) {
		SpringApplication.run(CadastroAutorApplication.class, args);
	}

}
