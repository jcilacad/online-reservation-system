package com.system.reservation.online;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ReservationSystem {

	public static void main(String[] args) {
		SpringApplication.run(ReservationSystem.class, args);
	}

}
