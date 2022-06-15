package dev.koicreek.bokasafn.mimirs.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MimirCatalogApplication {

	public static void main(String[] args) {
		 SpringApplication.run(MimirCatalogApplication.class, args);
	}

}
