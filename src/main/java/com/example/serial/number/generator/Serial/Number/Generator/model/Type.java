package com.example.serial.number.generator.Serial.Number.Generator.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "type_table")
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private boolean number = true;

    private boolean lowerCase = false;

    private boolean upperCase = false;

    private String exception = "";

    public void setException(String exception) {
        this.exception = exception;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isNumber() {
        return number;
    }

    public void setNumber(boolean number) {
        this.number = number;
    }

    public boolean isLowerCase() {
        return lowerCase;
    }

    public void setLowerCase(boolean lowerCase) {
        this.lowerCase = lowerCase;
    }

    public boolean isUpperCase() {
        return upperCase;
    }

    public void setUpperCase(boolean upperCase) {
        this.upperCase = upperCase;
    }

    public List<Character> getException() {

        List<Character> list = new ArrayList<Character>();
        for(char c : exception.toCharArray()) {
            list.add(c);
        }
        return list;
    }

    public void setException(List<Character> exception) {
        this.exception = String.valueOf(exception);
    }


}