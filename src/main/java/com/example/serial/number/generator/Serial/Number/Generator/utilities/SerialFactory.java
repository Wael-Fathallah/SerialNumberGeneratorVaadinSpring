package com.example.serial.number.generator.Serial.Number.Generator.utilities;

import com.example.serial.number.generator.Serial.Number.Generator.model.Serial;
import com.example.serial.number.generator.Serial.Number.Generator.model.Set;
import com.example.serial.number.generator.Serial.Number.Generator.model.Type;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class SerialFactory implements Runnable {

    private static SerialFactory self;

    private SerialFactory(){}

    //Singleton Method to return Object instance
    public static SerialFactory instance(){
        if (self !=null){
            return self;
        }else {
            self = new SerialFactory();
            return self;
        }
    }

    //Public Method to Generate a bundle of serial
    public List<Serial> generateSetOfSerial(Set set, int length, List<Serial> notAlowed){
//        List<String> notAlowedS = new ArrayList<>();
        HashSet notAlowedS = new HashSet<String>();
        for (Serial serial : notAlowed){
            notAlowedS.add(serial.getValue());
        }
        int size = set.getUnit();
        Type configuration = set.getType();
        Pattern pattern = pattern(configuration);

        //The maximum possibility for defined pattern (Not Used For Now)
        long numberOfPossibility = pattern.size ^ length;

        //Counter to prevent infinity loop in case of the lack of serial
        int outCounter = Constant.OUT_COUNTER;

        //++++++++++++++++++++++++++++++++++++++++++++++++++++//
        int outCounterPosition = 0;
        int generatedKeyPosition = 0;
        List<Serial> serials = new ArrayList<>();
        while (outCounterPosition < outCounter && generatedKeyPosition < size){
            String generated = generate(pattern.value, length);
            if (notAlowedS.add(generated)){

                outCounterPosition = 0;
                generatedKeyPosition++;
                Serial serial = new Serial();
                serial.setValue(generated);
                serial.setSet(set);
                serials.add(serial);
            } else {
                System.out.print("::: DUPLIC :::");
                outCounterPosition++;
            }
//            if (notAlowedS.contains(generated)){
//                outCounterPosition++;
//            }else {
//                outCounterPosition = 0;
//                generatedKeyPosition++;
//                Serial serial = new Serial();
//                serial.setValue(generated);
//                serial.setSet(set);
//                serials.add(serial);
//                notAlowedS.add(generated);
//            }

            System.out.println(generatedKeyPosition+" : "+generated);
        }
        //++++++++++++++++++++++++++++++++++++++++++++++++++++//
        return serials;
    }

    //Generate Single Serial based on Pattern & Length
    private String generate(String pattern, int length){

        StringBuilder serialGen = new StringBuilder();
        Random rnd = new Random();
        while (serialGen.length() < length) { // length of the random string.
            int index = (int) (rnd.nextFloat() * pattern.length());
            serialGen.append(pattern.charAt(index));
        }
        return serialGen.toString();
    }


    //Return The pattern from Type Object
    private Pattern pattern (Type configuration) {

        String allowedKey = (configuration.isNumber()) ? "0123456789" : "";
        allowedKey += (configuration.isUpperCase()) ? "ABCDEFGHIJKLMNOPQRSTUVWXYZ" : "";
        allowedKey += (configuration.isLowerCase()) ? "abcdefghijklmnopqrstuvwxyz" : "";

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
        return toReturn;
    }

    @Override
    public void run() {

    }

    //Inner Class (Pattern Struct)
    private class Pattern{
        String value;
        int size;
    }
}
