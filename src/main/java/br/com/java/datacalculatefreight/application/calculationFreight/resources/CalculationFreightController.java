package br.com.java.datacalculatefreight.application.calculationFreight.resources;

import br.com.java.datacalculatefreight.application.calculationFreight.CalculationFreightService;
import br.com.java.datacalculatefreight.utils.DefaultLog;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/calculation-freight", produces = MediaType.APPLICATION_JSON_VALUE)
public class CalculationFreightController {

    @Autowired
    DefaultLog defaultLog;
    
    @Autowired
    CalculationFreightService calculationFreightService;

    private static final String API_V1 = "/calculation-freight/v1/";

    @ApiOperation("Cálculo de frete por Id")
    @GetMapping(value = "/v1/{id}")
    public ResponseEntity<CalculationFreightResponse> getById(@Valid @PathVariable("id") Long id) {
        defaultLog.printLogExecutedEndpoint(HttpMethod.GET, API_V1);
        final CalculationFreightResponse calculationFreightResponse = calculationFreightService.getById(id);
        return ResponseEntity.ok(calculationFreightResponse);
    }

    @ApiOperation("Cálculo de frete listagem")
    @GetMapping(value = "/v1/")
    public ResponseEntity<Page<CalculationFreightResponse>> getAll(@RequestParam(required = false) Integer page,
                                                        @RequestParam(required = false) Integer size,
                                                        @RequestParam(required = false) String sortBy,
                                                        @RequestParam(required = false) String sortDirection) {
        return new ResponseEntity<>(calculationFreightService.getAll(page, size, sortBy, sortDirection), HttpStatus.OK);
    }
}