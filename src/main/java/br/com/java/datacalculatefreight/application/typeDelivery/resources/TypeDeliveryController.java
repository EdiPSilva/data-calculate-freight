package br.com.java.datacalculatefreight.application.typeDelivery.resources;

import br.com.java.datacalculatefreight.application.typeDelivery.TypeDeliveryService;
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
@RequestMapping(value = "/type-delivery", produces = MediaType.APPLICATION_JSON_VALUE)
public class TypeDeliveryController {

    @Autowired
    DefaultLog defaultLog;

    @Autowired
    TypeDeliveryService typeDeliveryService;

    private static final String API_V1 = "/type-delivery/v1/";

    @ApiOperation("Tipo entrega por Id")
    @GetMapping(value = "/v1/{id}")
    public ResponseEntity<TypeDeliveryResponse> getById(@Valid @PathVariable("id") Long id) {
        defaultLog.printLogExecutedEndpoint(HttpMethod.GET, API_V1);
        final TypeDeliveryResponse typeDeliveryResponse = typeDeliveryService.getById(id);
        return ResponseEntity.ok(typeDeliveryResponse);
    }

    @ApiOperation("Tipo entrega por tipo")
    @GetMapping(value = "/v1/type/{type}")
    public ResponseEntity<TypeDeliveryResponse> getByDocument(@Valid @PathVariable("type") String type) {
        defaultLog.printLogExecutedEndpoint(HttpMethod.GET, API_V1 + "type/");
        final TypeDeliveryResponse typeDeliveryResponse = typeDeliveryService.getByType(type);
        return ResponseEntity.ok(typeDeliveryResponse);
    }

    @ApiOperation("Tipo entrega listagem")
    @GetMapping(value = "/v1/")
    public ResponseEntity<Page<TypeDeliveryResponse>> getAll(@RequestParam(required = false) Integer page,
                                                             @RequestParam(required = false) Integer size,
                                                             @RequestParam(required = false) String sortBy,
                                                             @RequestParam(required = false) String sortDirection) {
        return new ResponseEntity<>(typeDeliveryService.getAll(page, size, sortBy, sortDirection), HttpStatus.OK);
    }

    @ApiOperation("Cadastro tipo entrega")
    @PostMapping(value = "/v1/")
    public ResponseEntity<TypeDeliveryResponse> create(@Valid @RequestBody TypeDeliveryRequest typeDeliveryRequest) {
        defaultLog.printLogExecutedEndpoint(HttpMethod.POST, API_V1);
        final TypeDeliveryResponse typeDeliveryResponse = typeDeliveryService.create(typeDeliveryRequest);
        return new ResponseEntity<>(typeDeliveryResponse, HttpStatus.CREATED);
    }

    @ApiOperation("Atualiza tipo entrega")
    @PutMapping(value = "/v1/{id}")
    public ResponseEntity<TypeDeliveryResponse> update(@Valid @PathVariable("id") Long id, @Valid @RequestBody TypeDeliveryRequest typeDeliveryRequest) {
        defaultLog.printLogExecutedEndpoint(HttpMethod.PUT, API_V1);
        final TypeDeliveryResponse typeDeliveryResponse = typeDeliveryService.update(id, typeDeliveryRequest);
        return new ResponseEntity<>(typeDeliveryResponse, HttpStatus.CREATED);
    }

    @ApiOperation("Remove tipo entrega")
    @DeleteMapping(value = "/v1/{id}")
    public ResponseEntity<DefaultResponse> delete(@Valid @PathVariable("id") Long id) {
        defaultLog.printLogExecutedEndpoint(HttpMethod.DELETE, API_V1);
        return new ResponseEntity<>(typeDeliveryService.delete(id), HttpStatus.ACCEPTED);
    }
}
