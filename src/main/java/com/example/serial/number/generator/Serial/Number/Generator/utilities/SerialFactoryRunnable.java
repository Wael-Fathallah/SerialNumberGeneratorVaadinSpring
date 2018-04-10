package com.example.serial.number.generator.Serial.Number.Generator.utilities;

import com.example.serial.number.generator.Serial.Number.Generator.model.Serial;
import com.example.serial.number.generator.Serial.Number.Generator.model.Set;
import com.example.serial.number.generator.Serial.Number.Generator.service.SetService;

import java.util.List;

public class SerialFactoryRunnable implements Runnable {

    private Set set;
    private int length;
    private List<Serial> notAlowed;
    private int setSize;
    private final SetService setService;

    public SerialFactoryRunnable(Set set, int length, List<Serial> notAlowed, int setSize, final SetService setService){
        this.set = set;
        this.length = length;
        this.notAlowed = notAlowed;
        this.setSize = setSize;
        this.setService = setService;
    }
    @Override
    public void run() {

        this.set.setSerials(SerialFactory.instance().generateSetOfSerial(this.set, this.length, this.notAlowed));
        if (this.set.getSerials().size() == this.setSize) {
            this.setService.saveSet(this.set);
            System.out.println("Success");
        } else
        {
            System.out.println("Failed");
        }
    }
}
