package dev.koicreek.bokasafn.mimir.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MimirGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MimirGatewayApplication.class, args);
	}

}
