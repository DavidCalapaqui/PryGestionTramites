package com.calapaqui.tramites;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
//aarranca la aplicacion para generar un ejectuable .jar
//@SpringBootApplication
//public class PryGestionTramitesApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(PryGestionTramitesApplication.class, args);
//	}
//
//}
//aarranca la aplicacion para generar un ejectuable .war
@SpringBootApplication
public class PryGestionTramitesApplication extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(PryGestionTramitesApplication.class);
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(PryGestionTramitesApplication.class, args);
	}

}
