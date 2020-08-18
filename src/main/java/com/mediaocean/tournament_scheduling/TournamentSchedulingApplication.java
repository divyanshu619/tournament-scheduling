package com.mediaocean.tournament_scheduling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.mediaocean.tournament_scheduling")
public class TournamentSchedulingApplication {

	public static void main(String[] args) {
		SpringApplication.run(TournamentSchedulingApplication.class, args);
	}

}
