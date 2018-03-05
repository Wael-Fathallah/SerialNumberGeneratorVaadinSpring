package com.example.serial.number.generator.Serial.Number.Generator.service;

import com.example.serial.number.generator.Serial.Number.Generator.model.Set;
import com.example.serial.number.generator.Serial.Number.Generator.repository.SetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetService {

    private final SetRepository setRepository;

    public SetService(final SetRepository setRepository) {
        this.setRepository = setRepository;
    }

    public Set getSetByName (String name) {
        return this.setRepository.findByName(name);
    }
    public void saveSet(Set set){
        this.setRepository.save(set);
    }

    public List<Set> getAllSet () {
        return this.setRepository.findAll();
    }

}
