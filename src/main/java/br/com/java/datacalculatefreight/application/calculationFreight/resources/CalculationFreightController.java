package br.com.java.datacalculatefreight.application.calculationFreight.resources;

import br.com.java.datacalculatefreight.utils.DefaultLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/calculation-freight", produces = MediaType.APPLICATION_JSON_VALUE)
public class CalculationFreightController {

    @Autowired
    DefaultLog defaultLog;

    private static final String API_V1 = "/calculation-freight/v1/";
}
