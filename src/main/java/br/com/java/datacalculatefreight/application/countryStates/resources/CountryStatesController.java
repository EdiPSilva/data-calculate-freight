package br.com.java.datacalculatefreight.application.countryStates.resources;

import br.com.java.datacalculatefreight.application.countryStates.CountryStatesService;
import br.com.java.datacalculatefreight.application.shippingCompany.resources.ShippingCompanyResponse;
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
@RequestMapping(value = "/country-states", produces = MediaType.APPLICATION_JSON_VALUE)
public class CountryStatesController {

    @Autowired
    DefaultLog defaultLog;

    @Autowired
    CountryStatesService countryStatesService;

    private static final String API_V1 = "/country-states/v1/";

    @ApiOperation("Estado por Id")
    @GetMapping(value = "/v1/{id}")
    public ResponseEntity<CountryStatesResponse> getById(@Valid @PathVariable("id") Long id) {
        defaultLog.printLogExecutedEndpoint(HttpMethod.GET, API_V1);
        final CountryStatesResponse countryStatesResponse = countryStatesService.getById(id);
        return ResponseEntity.ok(countryStatesResponse);
    }

    @ApiOperation("Estado por UF")
    @GetMapping(value = "/v1/uf/{UF}")
    public ResponseEntity<CountryStatesResponse> getByDocument(@Valid @PathVariable("UF") String uf) {
        defaultLog.printLogExecutedEndpoint(HttpMethod.GET, API_V1 + "document/");
        final CountryStatesResponse countryStatesResponse = countryStatesService.getByStateCode(uf);
        return ResponseEntity.ok(countryStatesResponse);
    }

    @ApiOperation("Estado listagem")
    @GetMapping(value = "/v1/")
    public ResponseEntity<Page<CountryStatesResponse>> getAll(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        return new ResponseEntity<>(countryStatesService.getAll(page, size), HttpStatus.OK);
    }
}