package com.example.serial.number.generator.Serial.Number.Generator.repository;

import com.example.serial.number.generator.Serial.Number.Generator.model.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SetRepository  extends JpaRepository<Set, Long> {
    public Set findByName(String name);
}
