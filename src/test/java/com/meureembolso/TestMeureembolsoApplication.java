package com.meureembolso;

import org.springframework.boot.SpringApplication;

public class TestMeureembolsoApplication {

	public static void main(String[] args) {
		SpringApplication.from(MeureembolsoApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
