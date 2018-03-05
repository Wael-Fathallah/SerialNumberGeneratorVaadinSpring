package com.example.serial.number.generator.Serial.Number.Generator.utilities;

import com.example.serial.number.generator.Serial.Number.Generator.model.Serial;
import com.example.serial.number.generator.Serial.Number.Generator.model.Set;
import com.example.serial.number.generator.Serial.Number.Generator.model.Type;
import com.example.serial.number.generator.Serial.Number.Generator.repository.SerialRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SerialFactory {

    private static SerialFactory self;

    private SerialFactory(){}

    public static SerialFactory instance(){
        if (self !=null){
            return self;
        }else {
            self = new SerialFactory();
            return self;
        }
    }

    public List<Serial> generateSetOfSerial(Set set, int length, List<Serial> notAlowed){
        List<String> notAlowedS = new ArrayList<>();
        for (Serial serial : notAlowed){
            notAlowedS.add(serial.getValue());
        }
        int size = set.getUnit();
        Type configuration = set.getType();
        Pattern pattern = pattern(configuration);
        long numberOfPossibility = pattern.size ^ length;
        int outCounter = Constant.OUT_COUNTER;
        //++++++++++++++++++++++++++++++++++++++++++++++++++++//
        int outCounterPosition = 0;
        int generatedKeyPosition = 0;
        List<Serial> serials = new ArrayList<>();
        while (outCounterPosition < outCounter && generatedKeyPosition < size){
            String generated = genarate(pattern.value, length);
            if (notAlowedS.contains(generated)){
                outCounterPosition++;
            }else {
                outCounterPosition = 0;
                generatedKeyPosition++;
                Serial serial = new Serial();
                serial.setValue(generated);
                serial.setSet(set);
                serials.add(serial);
                notAlowedS.add(generated);
            }
            System.out.println(generated);
        }
        //++++++++++++++++++++++++++++++++++++++++++++++++++++//
        return serials;
    }

    private String genarate(String pattern, int length){


        StringBuilder serialGen = new StringBuilder();
        Random rnd = new Random();
        while (serialGen.length() < length) { // length of the random string.
            int index = (int) (rnd.nextFloat() * pattern.length());
            serialGen.append(pattern.charAt(index));
        }
        return serialGen.toString();
    }



    private Pattern pattern (Type configuration) {

        String allowedKey = (configuration.isNumber()) ? "0123456789" : "";
        allowedKey += (configuration.isUpperCase()) ? "ABCDEFGHIJKLMNOPQRSTUVWXYZ" : "";
        allowedKey += (configuration.isLowerCase()) ? "abcdefghijklmnopqrstuvwxyz" : "";
        System.out.println(allowedKey);
        List<Character> list = new ArrayList<Character>();
        for(char c : allowedKey.toCharArray()) {
            list.add(c);
        }
        
        list.removeAll(configuration.getException());
        StringBuilder sb = new StringBuilder();
        for (Character ch: list) {
            sb.append(ch);
        }

        Pattern toReturn = new Pattern();
        toReturn.size = list.size();
        toReturn.value = sb.toString();
        System.out.println(toReturn.value);
        return toReturn;
    }

    private class Pattern{
        String value;
        int size;
    }
}
