package com.example.serial.number.generator.Serial.Number.Generator.view.screen;

import com.csvreader.CsvWriter;
import com.example.serial.number.generator.Serial.Number.Generator.model.Serial;
import com.example.serial.number.generator.Serial.Number.Generator.model.Set;
import com.example.serial.number.generator.Serial.Number.Generator.model.Type;
import com.example.serial.number.generator.Serial.Number.Generator.repository.SerialRepository;
import com.example.serial.number.generator.Serial.Number.Generator.repository.SetRepository;
import com.example.serial.number.generator.Serial.Number.Generator.utilities.AdvancedFileDownloader;
import com.example.serial.number.generator.Serial.Number.Generator.view.component.SerialManagerComponent;
import com.example.serial.number.generator.Serial.Number.Generator.view.component.SetsTableComponent;
import com.vaadin.annotations.Theme;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Theme("valo")
@SpringUI()
public class SerialNumberMangerScreen extends UI {

    @Inject
    SetRepository setRepository;
    @Inject
    SerialRepository serialRepository;

    private SerialManagerComponent serialManagerComponent;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        serialManagerComponent = new SerialManagerComponent();

        VerticalLayout layout = new VerticalLayout();
        serialManagerComponent.addComponent(new SetsTableComponent(setRepository.findAll()), "sets-table");
        layout.addComponent(serialManagerComponent);
        layout.setMargin(true);
        layout.setSpacing(false);
        setContent(layout);


        //Actions
        serialManagerComponent.getGenerate().addClickListener(clickEvent -> {

            SerialManagerComponent.Validator vld = serialManagerComponent.validator();
            List<Set> sets = setRepository.findAll();
            if (vld.state){
                Set set = serialManagerComponent.getSet(serialRepository.findAll());
                if(!setExists(sets, set.getName())){
                    if(set.getSerials().size() == set.getUnit()){
                        setRepository.save(set);
                        serialManagerComponent.addComponent(new SetsTableComponent(setRepository.findAll()), "sets-table");

                    } else {
                        Notification notification = new Notification("", "Out Of Range",
                                Notification.Type.ERROR_MESSAGE, true);

                        notification.show(Page.getCurrent());
                    }
                } else {
                    Notification notification = new Notification("", "Set Name Already Exist",
                            Notification.Type.ERROR_MESSAGE, true);

                    notification.show(Page.getCurrent());
                }


            }else {
                Notification notification = new Notification("", vld.message,
                        Notification.Type.ERROR_MESSAGE, true);

                notification.show(Page.getCurrent());
            }
        });


    }
    private boolean setExists(final List<Set> list, final String name){
        return list.stream().filter(o -> o.getName().equals(name)).findFirst().isPresent();
    }

}