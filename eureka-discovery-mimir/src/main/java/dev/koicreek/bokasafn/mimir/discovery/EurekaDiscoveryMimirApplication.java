package dev.koicreek.bokasafn.mimir.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaDiscoveryMimirApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaDiscoveryMimirApplication.class, args);
	}

}
