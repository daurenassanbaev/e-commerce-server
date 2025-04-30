package com.ecommerce.searchservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Search API",
				version = "1.0",
				description = "Documentation for Search REST API"
		)
)
public class SearchServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearchServiceApplication.class, args);
	}

}
