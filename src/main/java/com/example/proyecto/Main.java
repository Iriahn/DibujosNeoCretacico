package com.example.proyecto;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.proyecto.domain.Dibujo;
import com.example.proyecto.domain.EstiloEnum;
import com.example.proyecto.domain.CategoriaEnum;
import com.example.proyecto.services.DibujoService;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	// @Bean
	// CommandLineRunner initData(DibujoService dibujoService) {
	// 	return args -> {
	// 		Dibujo dib1 = new Dibujo(null, "Logo Zazian", "Pokemon", "Logo estilo pokemon", "Logo", EstiloEnum.OTRO, TecnicaEnum.DIGITAL, 2023, 10f, "1.png");
	// 		Dibujo dib2 = new Dibujo(null, "Kakashi", "Naruto", "Kakashi con estilo distinto", "Póster", EstiloEnum.ANIME, TecnicaEnum.MIXTO, 2024, 15f, "2.jpeg");
	// 		Dibujo dib3 = new Dibujo(null, "Brisa Musical", "Logo musical", "Logo grupo de música", "Logo", EstiloEnum.SEMIREALISTA, TecnicaEnum.DIGITAL, 2023, 10f, "3.jpg");
	// 		dibujoService.anadir(dib1);
	// 		dibujoService.anadir(dib2);
	// 		dibujoService.anadir(dib3);
	// 	};
	// }
}
