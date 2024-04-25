package dev.adryell.personalpage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@ConfigurationPropertiesScan({"dev.adryell.personalpage.config.GeneralConfig"})
public class PersonalPageApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalPageApplication.class, args);
	}

}
