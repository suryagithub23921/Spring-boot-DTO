package de.aoksystems.idm.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import de.aoksystems.idm.techbase.config.ExceptionConfiguration;
import de.aoksystems.idm.techbase.config.LoggingConfiguration;
import de.aoksystems.idm.techbase.config.ValidatorConfiguration;

@Import({LoggingConfiguration.class, ExceptionConfiguration.class, ValidatorConfiguration.class })
@SpringBootApplication
@ComponentScan({"de.aoksystems.idm.config"})
public class ConfigurationIdmApplication {	
	
	public static void main(String[] args) {
		SpringApplication.run(ConfigurationIdmApplication.class, args);
	}
}
