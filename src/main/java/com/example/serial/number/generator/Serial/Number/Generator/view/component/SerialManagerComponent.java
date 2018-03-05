package com.example.serial.number.generator.Serial.Number.Generator.view.component;

import com.example.serial.number.generator.Serial.Number.Generator.model.Serial;
import com.example.serial.number.generator.Serial.Number.Generator.model.Set;
import com.example.serial.number.generator.Serial.Number.Generator.repository.SetRepository;
import com.example.serial.number.generator.Serial.Number.Generator.utilities.Constant;
import com.example.serial.number.generator.Serial.Number.Generator.utilities.SerialFactory;
import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.shared.ui.slider.SliderOrientation;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.vaadin.freemarker.FreemarkerLayout;
import org.vaadin.ui.NumberField;

import javax.inject.Inject;
import java.util.List;


@JavaScript({"https://code.jquery.com/jquery-3.2.1.slim.min.js",
        "https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js",
        "https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"})

@StyleSheet({"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"})

public class SerialManagerComponent extends FreemarkerLayout{

    private TextField setNameField;
    private NumberField setNumberField;
    private Slider length;
    private Button generate;
    private Button cancel;
    private ConfigurationComponent configurationComponent;

    @Inject
    SetRepository setRepository;


    public SerialManagerComponent() {
        super("templates/serial-manager.html");

        setNameField = new TextField();
        setNameField.addStyleName("form-control");
        setNameField.setWidth("90%");
        setNameField.setPlaceholder("Enter Set Name");
        addComponent(setNameField, "set-name-field");

        setNumberField = new NumberField();
        setNumberField.setDecimalPrecision(2);
        setNumberField.setDecimalSeparator(',');
        setNumberField.setGroupingSeparator('.');
        setNumberField.setDecimalSeparatorAlwaysShown(true);
        setNumberField.addStyleName("form-control");
        setNumberField.setWidth("90%");
        setNumberField.setMinValue(10);
        setNumberField.setPlaceholder("Enter Set Size");
        addComponent(setNumberField, "set-number-field");

        length = new Slider(Constant.MIN_SERIAL_LENGTH, Constant.MAX_SERIAL_LENGTH);

        length.setOrientation(SliderOrientation.HORIZONTAL);
        length.setWidth("90%");
        length.setValue((double) Constant.DEFAULT_SERIAL_LENGTH);
        addComponent(length, "set-length-slider");
        length.setValue((double) Constant.DEFAULT_SERIAL_LENGTH);

        generate = new Button("Generate");
        generate.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        addComponent(generate, "set-generate-button");

        cancel = new Button("Cancel");
        cancel.addStyleName(ValoTheme.BUTTON_DANGER);
        addComponent(cancel, "set-cancel-button");

        configurationComponent = new ConfigurationComponent();
        addComponent(configurationComponent, "set-confuguration-component");

        //Actions
        cancel.addClickListener(clickEvent -> {
            System.out.println("cancel");
            setNameField.setValue("");
            setNumberField.setValue("");
            length.setValue((double) Constant.DEFAULT_SERIAL_LENGTH);
        });

    }
    public Validator validator () {
        Validator vld = new Validator();
        if (setNameField.getValue() == ""){
            vld.message = "Please Enter Set Name";
            vld.state = false;
        }else if (setNumberField.getValue() == ""){
            vld.message = "Please Enter Set Size";
            vld.state = false;
        }
        return vld;
    }

    public Button getGenerate() {
        return generate;
    }

    public Set getSet(List<Serial> notAlowed){
        Set set = new Set();
        set.setName(setNameField.getValue());
        set.setUnit(Integer.valueOf(setNumberField.getValue()));
        set.setType(configurationComponent.getConfiguration());
        set.setSerials(SerialFactory.instance().generateSetOfSerial(
                set,
                length.getValue().intValue(),
                notAlowed
        ));
        return set;
    }
    public class Validator {
        public boolean state = true;
        public String message = "";
    }


}