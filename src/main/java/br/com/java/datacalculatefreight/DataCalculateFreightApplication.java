package br.com.java.datacalculatefreight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class DataCalculateFreightApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataCalculateFreightApplication.class, args);
	}

}
