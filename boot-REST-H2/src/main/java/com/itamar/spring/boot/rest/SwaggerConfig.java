package com.itamar.spring.boot.rest;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.itamar.spring.boot.rest")).paths(regex("/items.*"))
				.build().apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {

		ApiInfo apiInfo = new ApiInfo("Spring Boot REST H2 application",
				"An exercise project exposing API for manipulating Items in an inventory. The project is a lightweight project which implements REST API, JPA-data implemented by hibernate and a testing class. Security - was not implemented.\r\n"
						+ "In order to have initial data in the H2 DB added a file src/main/resources/data.sql with 4 items. H2 recognize this file and runs the insert commands after startup.\r\n"
						+ "Item structure: private Long number; private String name; private Long amount; private String inventorycode;",
				"1.0", "", new Contact("Itamar Lev", "https://github.com/itamarlev/", "Itamar.lev@gmail.com"),
				"Apache License Version 2.0", "https://www.apache.org/licenses/LICENSE-2.0", Collections.emptyList());
		return apiInfo;
	}
}
