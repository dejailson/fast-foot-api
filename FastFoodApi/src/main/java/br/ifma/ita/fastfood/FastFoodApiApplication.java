package br.ifma.ita.fastfood;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import br.ifma.ita.fastfood.domain.infraestrutura.JpaRepositorioCustomizadoImpl;

//https://github.com/brunokarpo/spring-boot-com-TDD
@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = JpaRepositorioCustomizadoImpl.class)
public class FastFoodApiApplication implements CommandLineRunner{
	

	public static void main(String[] args) {
		SpringApplication.run(FastFoodApiApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {}
}
