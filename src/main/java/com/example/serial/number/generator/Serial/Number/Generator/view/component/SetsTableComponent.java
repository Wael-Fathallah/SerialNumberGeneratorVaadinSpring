package com.example.serial.number.generator.Serial.Number.Generator.view.component;

import com.example.serial.number.generator.Serial.Number.Generator.model.Serial;
import com.example.serial.number.generator.Serial.Number.Generator.model.Set;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.csvreader.CsvWriter;

import com.example.serial.number.generator.Serial.Number.Generator.utilities.AdvancedFileDownloader;
import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.ui.Button;
import com.vaadin.ui.themes.ValoTheme;
import org.vaadin.freemarker.FreemarkerLayout;

import java.util.List;

@JavaScript({"vaadin://js/jquery-1.12.3.min.js", "vaadin://js/jquery-ui.js"})
@StyleSheet({"vaadin://css/util.css", "vaadin://css/main.css"})
public class SetsTableComponent extends FreemarkerLayout {

    private List<Set> sets;

    public SetsTableComponent(List<Set> sets) {
        super("templates/sets-table.html");

        this.sets = sets;

        for (Set set : sets) {
            Button downloadButton = new Button("Get CSV");
            downloadButton.addStyleNames(ValoTheme.BUTTON_LINK);

            final AdvancedFileDownloader downloader = new AdvancedFileDownloader();
            downloader
                    .addAdvancedDownloaderListener(new AdvancedFileDownloader.AdvancedDownloaderListener() {

                        @Override
                        public void beforeDownload(AdvancedFileDownloader.DownloaderEvent downloadEvent) {

                            String outputFile = set.getName()+".csv";

                            File outFile = new File(outputFile);
                            if (outFile.exists()) {
                                outFile.delete();
                            }
                            try {
                                CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, false), ',');
                                csvOutput.write("Id");
                                csvOutput.write("Serial");
                                csvOutput.endRecord();
                                for (Serial serial: set.getSerials()) {
                                    csvOutput.write(String.valueOf(serial.getId()));
                                    csvOutput.write(serial.getValue());
                                    csvOutput.endRecord();
                                }
                                csvOutput.close();

                                downloader.setFilePath(outputFile);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                    });

            downloader.extend(downloadButton);

            addComponent(downloadButton, "download-button-" + set.getId());
        }
    }

    public List<Set> getSets() {
        return sets;
    }

}