package com.example.serial.number.generator.Serial.Number.Generator.repository;

import com.example.serial.number.generator.Serial.Number.Generator.model.Serial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SerialRepository  extends JpaRepository<Serial, Long> {
}
