package dev.adryell.personalpage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class PersonalPageApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalPageApplication.class, args);
	}

}
