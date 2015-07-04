package br.ufscar.aplicacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "br.ufscar")
//@EntityScan(basePackages = "br.ufscar")
public class Application {

	public static void main(String[] args) {
		@SuppressWarnings("unused")
		ApplicationContext ctx = SpringApplication.run(Application.class, args);
	}
}
