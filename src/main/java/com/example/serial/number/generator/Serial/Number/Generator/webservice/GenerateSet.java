package com.example.serial.number.generator.Serial.Number.Generator.webservice;

import com.example.serial.number.generator.Serial.Number.Generator.model.Serial;
import com.example.serial.number.generator.Serial.Number.Generator.model.Set;
import com.example.serial.number.generator.Serial.Number.Generator.model.Type;
import com.example.serial.number.generator.Serial.Number.Generator.service.SerialService;
import com.example.serial.number.generator.Serial.Number.Generator.service.SetService;
import com.example.serial.number.generator.Serial.Number.Generator.utilities.Constant;
import com.example.serial.number.generator.Serial.Number.Generator.utilities.SerialFactory;
import com.example.serial.number.generator.Serial.Number.Generator.webservice.request.CreateSetModel;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/generate")
public class GenerateSet {

    private final SetService setService;
    private final SerialService serialService;

    public GenerateSet(final SetService setService, final SerialService serialService) {
        this.setService = setService;
        this.serialService = serialService;
    }

    @GetMapping(value = "/{set_name}/{set_size}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String createSet(@PathVariable("set_name") String setName, @PathVariable("set_size") int set_size) {

        List<Serial> serials = this.serialService.getAllSerial();
        List<Set> sets = this.setService.getAllSet();

        return createSet(setName, set_size, serials, sets);

    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public String createSet(@RequestBody CreateSetModel createSetModel) {

        List<Serial> serials = this.serialService.getAllSerial();
        List<Set> sets = this.setService.getAllSet();

        int set_size = createSetModel.getSetSize();
        String setName = createSetModel.getSetName();

        return createSet(setName, set_size, serials, sets);

    }

    private String createSet(String setName, int set_size, List<Serial> serials, List<Set> sets) {
        if ( (serials.size() + set_size) <= Constant.SERIAL_GENERATED_LIMIT ){
            if (!setExists(sets, setName)){
                Set set = new Set();
                set.setName(setName);
                set.setUnit(set_size);
                Type type = new Type();
                set.setType(type);
                set.setSerials(SerialFactory.instance().generateSetOfSerial(set, 12, this.serialService.getAllSerial()));
                if (set.getSerials().size() == set_size) {
                    this.setService.saveSet(set);
                    return "Success " + setName + " : " + set_size;
                } else
                {
                    return "Failed " + setName + " : " + set_size;
                }
            } else {
                return "Failed : Set Name Already Exist";
            }

        } else {
            return "Failed : You reach the limit of generated serial " + Constant.SERIAL_GENERATED_LIMIT;
        }
    }

    private boolean setExists(final List<Set> list, final String name){
        return list.stream().filter(o -> o.getName().equals(name)).findFirst().isPresent();
    }
}

