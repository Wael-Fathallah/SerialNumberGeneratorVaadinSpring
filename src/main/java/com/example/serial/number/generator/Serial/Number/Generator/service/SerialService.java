package com.example.serial.number.generator.Serial.Number.Generator.service;

import com.example.serial.number.generator.Serial.Number.Generator.model.Serial;
import com.example.serial.number.generator.Serial.Number.Generator.repository.SerialRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerialService {
    private final SerialRepository serialRepository;

    public SerialService(final SerialRepository serialRepository) {
        this.serialRepository = serialRepository;
    }

    public List<Serial> getAllSerial () {
        return this.serialRepository.findAll();
    }
}
