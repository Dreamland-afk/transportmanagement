package com.dreamquest.transportproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




//@SpringBootApplication(exclude = {
//DataSourceAutoConfiguration.class, 
//DataSourceTransactionManagerAutoConfiguration.class, 
//HibernateJpaAutoConfiguration.class
//})
@SpringBootApplication
public class TransportprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransportprojectApplication.class, args);
	}

}
