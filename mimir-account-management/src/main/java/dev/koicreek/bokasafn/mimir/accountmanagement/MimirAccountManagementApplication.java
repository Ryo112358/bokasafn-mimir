package dev.koicreek.bokasafn.mimir.accountmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MimirAccountManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(MimirAccountManagementApplication.class, args);
	}

}
