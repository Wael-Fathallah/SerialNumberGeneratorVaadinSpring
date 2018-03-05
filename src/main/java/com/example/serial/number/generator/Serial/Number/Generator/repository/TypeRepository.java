package com.example.serial.number.generator.Serial.Number.Generator.repository;

import com.example.serial.number.generator.Serial.Number.Generator.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository  extends JpaRepository<Type, Long> {
}