package br.com.java.datacalculatefreight.application.freightRoute.resources;

import br.com.java.datacalculatefreight.application.freightRoute.FreightRouteService;
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
@RequestMapping(value = "/freight-route", produces = MediaType.APPLICATION_JSON_VALUE)
public class FreightRouteController {

    @Autowired
    DefaultLog defaultLog;

    @Autowired
    FreightRouteService freightRouteService;

    private static final String API_V1 = "/shipping-company/v1/";
    private static final String START_POSTAL_CODE = "start";
    private static final String END_POSTAL_CODE = "end";

    @ApiOperation("Rota frete por Id")
    @GetMapping(value = "/v1/{id}")
    public ResponseEntity<FreightRouteResponse> getById(@Valid @PathVariable("id") Long id) {
        defaultLog.printLogExecutedEndpoint(HttpMethod.GET, API_V1);
        final FreightRouteResponse freightRouteResponse = freightRouteService.getById(id);
        return ResponseEntity.ok(freightRouteResponse);
    }

    @ApiOperation("Rota frete por cep inicial")
    @GetMapping(value = "/v1/start-postal-code/{code}")
    public ResponseEntity<FreightRouteResponse> getByStartPostalCode(@Valid @PathVariable("code") String code) {
        return getByPostalCode(code, START_POSTAL_CODE, "end-postal-code/");
    }

    @ApiOperation("Rota frete por cep final")
    @GetMapping(value = "/v1/end-postal-code/{code}")
    public ResponseEntity<FreightRouteResponse> getByEndPostalCode(@Valid @PathVariable("CEP") String code) {
        return getByPostalCode(code, END_POSTAL_CODE, "end-postal-code/");
    }

    private ResponseEntity<FreightRouteResponse> getByPostalCode(final String code, final String column, final String api) {
        defaultLog.printLogExecutedEndpoint(HttpMethod.GET, API_V1 + api);
        final FreightRouteResponse freightRouteResponse = freightRouteService.getByPostalCode(code, column);
        return ResponseEntity.ok(freightRouteResponse);
    }

    @ApiOperation("Rota frete listagem")
    @GetMapping(value = "/v1/")
    public ResponseEntity<Page<FreightRouteResponse>> getAll(@RequestParam(required = false) Integer page,
                                                                @RequestParam(required = false) Integer size,
                                                                @RequestParam(required = false) String sortBy,
                                                                @RequestParam(required = false) String sortDirection) {
        return new ResponseEntity<>(freightRouteService.getAll(page, size, sortBy, sortDirection), HttpStatus.OK);
    }

    @ApiOperation("Cadastro rota frete")
    @PostMapping(value = "/v1/")
    public ResponseEntity<FreightRouteResponse> create(@Valid @RequestBody FreightRouteRequest freightRouteRequest) {
        defaultLog.printLogExecutedEndpoint(HttpMethod.POST, API_V1);
        final FreightRouteResponse freightRouteResponse = freightRouteService.create(freightRouteRequest);
        return new ResponseEntity<>(freightRouteResponse, HttpStatus.CREATED);
    }

    @ApiOperation("Atualiza rota frete")
    @PutMapping(value = "/v1/{id}")
    public ResponseEntity<FreightRouteResponse> update(@Valid @PathVariable("id") Long id, @Valid @RequestBody FreightRouteRequest freightRouteRequest) {
        defaultLog.printLogExecutedEndpoint(HttpMethod.PUT, API_V1);
        final FreightRouteResponse freightRouteResponse = freightRouteService.update(id, freightRouteRequest);
        return new ResponseEntity<>(freightRouteResponse, HttpStatus.CREATED);
    }

    @ApiOperation("Remove rota frete")
    @DeleteMapping(value = "/v1/{id}")
    public ResponseEntity<DefaultResponse> delete(@Valid @PathVariable("id") Long id) {
        defaultLog.printLogExecutedEndpoint(HttpMethod.DELETE, API_V1);
        return new ResponseEntity<>(freightRouteService.delete(id), HttpStatus.ACCEPTED);
    }
}
