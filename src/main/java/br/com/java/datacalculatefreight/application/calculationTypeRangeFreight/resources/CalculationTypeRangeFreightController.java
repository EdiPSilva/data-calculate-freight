package br.com.java.datacalculatefreight.application.calculationTypeRangeFreight.resources;

import br.com.java.datacalculatefreight.application.calculationTypeRangeFreight.CalculationTypeRangeFreightService;
import br.com.java.datacalculatefreight.utils.DefaultLog;
import br.com.java.datacalculatefreight.utils.DefaultResponse;
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
@RequestMapping(value = "/calculation-type-range-freight", produces = MediaType.APPLICATION_JSON_VALUE)
public class CalculationTypeRangeFreightController {

    @Autowired
    private DefaultLog defaultLog;

    @Autowired
    private CalculationTypeRangeFreightService calculationTypeRangeFreightService;

    private static final String API_V1 = "/calculation-type-range-freight/v1/";

    @ApiOperation("Tipo de cálculo renge frete por Id")
    @GetMapping(value = "/v1/{id}")
    public ResponseEntity<CalculationTypeRangeFreightResponse> getById(@Valid @PathVariable("id") Long id) {
        defaultLog.printLogExecutedEndpoint(HttpMethod.GET, API_V1);
        final CalculationTypeRangeFreightResponse calculationTypeRangeFreightResponse = calculationTypeRangeFreightService.getById(id);
        return ResponseEntity.ok(calculationTypeRangeFreightResponse);
    }

    @ApiOperation("Tipo de cálculo renge frete listagem")
    @GetMapping(value = "/v1/")
    public ResponseEntity<Page<CalculationTypeRangeFreightResponse>> getAll(@RequestParam(required = false) Integer page,
                                                             @RequestParam(required = false) Integer size,
                                                             @RequestParam(required = false) String sortBy,
                                                             @RequestParam(required = false) String sortDirection) {
        return new ResponseEntity<>(calculationTypeRangeFreightService.getAll(page, size, sortBy, sortDirection), HttpStatus.OK);
    }

    @ApiOperation("Cadastro Tipo de cálculo renge frete")
    @PostMapping(value = "/v1/")
    public ResponseEntity<CalculationTypeRangeFreightResponse> create(@Valid @RequestBody CalculationTypeRangeFreightRequest calculationTypeRangeFreightRequest) {
        defaultLog.printLogExecutedEndpoint(HttpMethod.POST, API_V1);
        final CalculationTypeRangeFreightResponse calculationTypeRangeFreightResponse = calculationTypeRangeFreightService.create(calculationTypeRangeFreightRequest);
        return new ResponseEntity<>(calculationTypeRangeFreightResponse, HttpStatus.CREATED);
    }

    @ApiOperation("Atualiza Tipo de cálculo renge frete")
    @PutMapping(value = "/v1/{id}")
    public ResponseEntity<CalculationTypeRangeFreightResponse> update(@Valid @PathVariable("id") Long id, @Valid @RequestBody CalculationTypeRangeFreightRequest calculationTypeRangeFreightRequest) {
        defaultLog.printLogExecutedEndpoint(HttpMethod.PUT, API_V1);
        final CalculationTypeRangeFreightResponse calculationTypeRangeFreightResponse = calculationTypeRangeFreightService.update(id, calculationTypeRangeFreightRequest);
        return new ResponseEntity<>(calculationTypeRangeFreightResponse, HttpStatus.CREATED);
    }

    @ApiOperation("Remove Tipo de cálculo renge frete")
    @DeleteMapping(value = "/v1/{id}")
    public ResponseEntity<DefaultResponse> delete(@Valid @PathVariable("id") Long id) {
        defaultLog.printLogExecutedEndpoint(HttpMethod.DELETE, API_V1);
        return new ResponseEntity<>(calculationTypeRangeFreightService.delete(id), HttpStatus.ACCEPTED);
    }
}
