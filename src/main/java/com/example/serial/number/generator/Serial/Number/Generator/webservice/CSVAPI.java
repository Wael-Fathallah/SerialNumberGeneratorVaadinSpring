package com.example.serial.number.generator.Serial.Number.Generator.webservice;

import com.example.serial.number.generator.Serial.Number.Generator.model.Serial;
import com.example.serial.number.generator.Serial.Number.Generator.model.Set;
import com.example.serial.number.generator.Serial.Number.Generator.service.SetService;


import org.springframework.web.bind.annotation.*;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;

import java.io.*;

@RestController
@RequestMapping("/csv")
public class CSVAPI {
    private final SetService setService;

    public CSVAPI(final SetService setService) {
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
