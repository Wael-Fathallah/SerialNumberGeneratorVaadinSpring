package com.example.serial.number.generator.Serial.Number.Generator.webservice;

import com.example.serial.number.generator.Serial.Number.Generator.model.Serial;
import com.example.serial.number.generator.Serial.Number.Generator.model.Set;
import com.example.serial.number.generator.Serial.Number.Generator.model.Type;
import com.example.serial.number.generator.Serial.Number.Generator.service.SetService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.io.*;
import java.net.URI;

@RestController
@RequestMapping("/csv")
public class CSVService {
    private final SetService setService;

    public CSVService(final SetService setService) {
        this.setService = setService;
    }
    @RequestMapping(value = "/{set_name}")
    public String downloadCSV(HttpServletResponse response, @PathVariable("set_name") String setName) throws IOException {
        Set set = this.setService.getSetByName(setName);
        if(set != null) {
            String outputFile = set.getName() + ".csv";
            response.setContentType("text/csv");

            // creates mock data
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"",
                    outputFile);
            response.setHeader(headerKey, headerValue);


            // uses the Super CSV API to generate CSV data from the model data
            ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(),
                    CsvPreference.STANDARD_PREFERENCE);

            String[] header = { "Id", "Value"};

            csvWriter.writeHeader(header);
            for (Serial serial : set.getSerials()) {
                csvWriter.write(serial, header);
            }

            csvWriter.close();
            return "Success ";
        } else {
            return "Failed : Set Not Available ";
        }

    }

}
