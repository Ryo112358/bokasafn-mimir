package dev.koicreek.bokasafn.mimirs.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MimirsGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MimirsGatewayApplication.class, args);
	}

}
