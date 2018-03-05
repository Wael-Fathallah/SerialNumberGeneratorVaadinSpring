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

import java.util.List;

@JavaScript({"https://code.jquery.com/jquery-3.2.1.slim.min.js",
        "https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js",
        "https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"})

@StyleSheet({"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"})

public class SerialManagerComponent extends FreemarkerLayout{

    private Label nameFieldTitle;
    private Label numberFieldTitle;
    private Label lenghtSliderTitle;
    private TextField setNameField;
    private NumberField setNumberField;
    private Slider length;
    private Button generate;
    private Button cancel;
    private ConfigurationComponent configurationComponent;

    public SerialManagerComponent() {
        super("templates/serial-manager.html");

        //Create Form & Inserted In Template
        addComponent(creatForm(), "set-form");

        //Import configuration Form & Inserted In Template
        configurationComponent = new ConfigurationComponent();
        addComponent(configurationComponent, "set-confuguration-component");

        //Inserted Generate Button In Template
        generate = new Button("Generate");
        generate.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        addComponent(generate, "set-generate-button");

        //Inserted Cancel Button In Template
        cancel = new Button("Cancel");
        cancel.addStyleName(ValoTheme.BUTTON_DANGER);
        addComponent(cancel, "set-cancel-button");

        //Actions
        cancel.addClickListener(clickEvent -> {
            System.out.println("cancel");
            setNameField.setValue("");
            setNumberField.setValue("");
            length.setValue((double) Constant.DEFAULT_SERIAL_LENGTH);
        });

    }

    //Validation Method of form inputs
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

    public Set getSet(List<Serial> notAllowed){
        Set set = new Set();
        set.setName(setNameField.getValue());
        set.setUnit(Integer.valueOf(setNumberField.getValue()));
        set.setType(configurationComponent.getConfiguration());
        set.setSerials(SerialFactory.instance().generateSetOfSerial(
                set,
                length.getValue().intValue(),
                notAllowed
        ));
        return set;
    }

    //Inner Class (struct for Validation)
    public class Validator {
        public boolean state = true;
        public String message = "";
    }

    //Fom factory method
    private VerticalLayout creatForm(){

        VerticalLayout form = new VerticalLayout();

        nameFieldTitle = new Label("Serial Set Name");
        setNameField = new TextField();
        setNameField.addStyleName("form-control");
        setNameField.setWidth("90%");
        setNameField.setPlaceholder("Enter Set Name");
        form.addComponent(nameFieldTitle);
        form.addComponent(setNameField);

        numberFieldTitle = new Label("Number Of Serials");
        setNumberField = new NumberField();
        setNumberField.setDecimalPrecision(2);
        setNumberField.setDecimalSeparator(',');
        setNumberField.setGroupingSeparator('.');
        setNumberField.setDecimalSeparatorAlwaysShown(true);
        setNumberField.setMinValue(10);

        setNumberField.addStyleName("form-control");
        setNumberField.setWidth("90%");
        setNumberField.setPlaceholder("Enter Set Size");
        form.addComponent(numberFieldTitle);
        form.addComponent(setNumberField);

        lenghtSliderTitle = new Label("Length Of Serials");
        length = new Slider(Constant.MIN_SERIAL_LENGTH, Constant.MAX_SERIAL_LENGTH);

        length.setOrientation(SliderOrientation.HORIZONTAL);
        length.setWidth("90%");
        length.setValue((double) Constant.DEFAULT_SERIAL_LENGTH);
        form.addComponent(lenghtSliderTitle);
        form.addComponent(length);
        length.setValue((double) Constant.DEFAULT_SERIAL_LENGTH);

        return form;
    }

}