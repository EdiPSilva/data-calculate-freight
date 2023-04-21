package br.com.java.datacalculatefreight.application.rangeFreight.resources;

import br.com.java.datacalculatefreight.application.rangeFreight.RangeFreightService;
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
@RequestMapping(value = "/range-freight", produces = MediaType.APPLICATION_JSON_VALUE)
public class RangeFreightController {

    @Autowired
    private DefaultLog defaultLog;

    @Autowired
    private RangeFreightService rangeFreightService;

    private static final String API_V1 = "/range-freight/v1/";

    @ApiOperation("Range frete por Id")
    @GetMapping(value = "/v1/{id}")
    public ResponseEntity<RangeFreightResponse> getById(@Valid @PathVariable("id") Long id) {
        defaultLog.printLogExecutedEndpoint(HttpMethod.GET, API_V1);
        final RangeFreightResponse rangeFreightResponse = rangeFreightService.getById(id);
        return ResponseEntity.ok(rangeFreightResponse);
    }

    @ApiOperation("Range frete listagem")
    @GetMapping(value = "/v1/")
    public ResponseEntity<Page<RangeFreightResponse>> getAll(@RequestParam(required = false) Integer page,
                                                             @RequestParam(required = false) Integer size,
                                                             @RequestParam(required = false) String sortBy,
                                                             @RequestParam(required = false) String sortDirection) {
        return new ResponseEntity<>(rangeFreightService.getAll(page, size, sortBy, sortDirection), HttpStatus.OK);
    }
    
    @ApiOperation("Cadastro range frete")
    @PostMapping(value = "/v1/")
    public ResponseEntity<RangeFreightResponse> create(@Valid @RequestBody RangeFreightRequest rangeFreightRequest) {
        defaultLog.printLogExecutedEndpoint(HttpMethod.POST, API_V1);
        final RangeFreightResponse rangeFreightResponse = rangeFreightService.create(rangeFreightRequest);
        return new ResponseEntity<>(rangeFreightResponse, HttpStatus.CREATED);
    }

    @ApiOperation("Atualiza range frete")
    @PutMapping(value = "/v1/{id}")
    public ResponseEntity<RangeFreightResponse> update(@Valid @PathVariable("id") Long id, @Valid @RequestBody RangeFreightRequest rangeFreightRequest) {
        defaultLog.printLogExecutedEndpoint(HttpMethod.PUT, API_V1);
        final RangeFreightResponse rangeFreightResponse = rangeFreightService.update(id, rangeFreightRequest);
        return new ResponseEntity<>(rangeFreightResponse, HttpStatus.CREATED);
    }

    @ApiOperation("Remove range frete")
    @DeleteMapping(value = "/v1/{id}")
    public ResponseEntity<DefaultResponse> delete(@Valid @PathVariable("id") Long id) {
        defaultLog.printLogExecutedEndpoint(HttpMethod.DELETE, API_V1);
        return new ResponseEntity<>(rangeFreightService.delete(id), HttpStatus.ACCEPTED);
    }
}
