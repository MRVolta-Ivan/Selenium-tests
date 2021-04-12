package com.autorepairportal.autorepairportal;

import org.hibernate.cfg.NamingStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.util.ParsingUtils;

@SpringBootApplication
public class AutorepairportalApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutorepairportalApplication.class, args);
	}
}
