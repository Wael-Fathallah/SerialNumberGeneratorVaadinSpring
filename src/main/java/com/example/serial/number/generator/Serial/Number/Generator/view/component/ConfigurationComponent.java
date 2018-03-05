package com.example.serial.number.generator.Serial.Number.Generator.view.component;

import com.example.serial.number.generator.Serial.Number.Generator.model.Type;
import com.vaadin.ui.*;

public class ConfigurationComponent  extends VerticalLayout {

    private CheckBox number;
    private CheckBox lowerCase;
    public CheckBox upperCase;
    private TextField exception;

    //Create Configuration Form
    public ConfigurationComponent(){

        Label title = new Label("Configuration");
        number = new CheckBox("Numbers");
        lowerCase = new CheckBox("Lower Case");
        upperCase = new CheckBox("Upper Case");
        exception = new TextField("Exclude Characters");
        exception.setPlaceholder("eg. O, o, i, I, L, etc");
        addComponents(title);
        addComponents(number);
        addComponents(lowerCase);
        addComponents(upperCase);
        addComponents(exception);
        number.setValue(true);
    }

    //Public method to return the configuration
    public Type getConfiguration(){
        Type type = new Type();
        if (!lowerCase.getValue() && !upperCase.getValue() && !number.getValue() ){
            number.setValue(true);
        }
        type.setNumber(number.getValue());
        type.setLowerCase(lowerCase.getValue());
        type.setUpperCase(upperCase.getValue());
        type.setException(exception.getValue());
        return type;
    }
}