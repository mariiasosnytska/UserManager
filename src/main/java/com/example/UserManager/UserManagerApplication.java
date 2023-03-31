package com.example.UserManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;

import java.util.Arrays;

@SpringBootApplication
@PropertySource("classpath:application.yaml")
public class UserManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserManagerApplication.class, args);
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		//context.getEnvironment().setActiveProfiles("template");
		System.out.println("Active profiles: " + Arrays.toString(context.getEnvironment().getActiveProfiles()));
	}
}
