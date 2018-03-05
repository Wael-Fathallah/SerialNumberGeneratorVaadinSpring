package com.example.serial.number.generator.Serial.Number.Generator;

import com.example.serial.number.generator.Serial.Number.Generator.utilities.SerialFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.inject.Inject;

@SpringBootApplication
public class SerialNumberGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SerialNumberGeneratorApplication.class, args);
	}

}
