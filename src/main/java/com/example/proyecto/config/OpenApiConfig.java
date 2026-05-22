package com.example.proyecto.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

	@Bean
	OpenAPI myOpenAPI() {
		Server prodServer = new Server();
		prodServer.setUrl("http://localhost:9000");
		prodServer.setDescription("Server URL in Production environment");
		Contact contact = new Contact();
		contact.setEmail("iriahn22@gmail.com");
		contact.setName("Iria Hidalgo");
		contact.setUrl("https://www.mycompany.com");
		License mitlicense = new License()
				.name("MIT License")
				.url("https://choosealicense.com/licenses/mit/");

		Info info = new Info()
				.title("Ejemplo de documentación API")
				.version("l.8")
				.contact(contact)
				.description("Esta API es un ejemplo del uso de Swagger")
				.termsOfService("https://www.mycompany.com/terms")
				.license(mitlicense);
		return new OpenAPI().info(info).servers(List.of(prodServer));
	}
}
