package br.com.hrom.simiantester;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SimianTesterApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimianTesterApplication.class, args);
	}

}
