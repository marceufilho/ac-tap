package br.edu.ibmec.universidade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(
        scanBasePackages = "br.edu.ibmec",
        exclude = {DataSourceAutoConfiguration.class}
)
public class UniversidadeApplication {

	public static void main(String[] args) {
        SpringApplication.run(UniversidadeApplication.class, args);
	}

}
