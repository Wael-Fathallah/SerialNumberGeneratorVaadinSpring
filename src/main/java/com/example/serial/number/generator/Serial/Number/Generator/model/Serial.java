package com.example.serial.number.generator.Serial.Number.Generator.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "serial_table")
public class Serial {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private  String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "set_id")
    private Set set;

    //Getter & Setter
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Set getSet() {
        return set;
    }

    public void setSet(Set set) {
        this.set = set;
    }
}
