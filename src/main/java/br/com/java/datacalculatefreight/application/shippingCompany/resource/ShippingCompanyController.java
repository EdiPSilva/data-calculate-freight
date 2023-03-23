package br.com.java.datacalculatefreight.application.shippingCompany.resource;

import br.com.java.datacalculatefreight.application.shippingCompany.ShippingCompanyService;
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
@RequestMapping(value = "/shipping-company", produces = MediaType.APPLICATION_JSON_VALUE)
public class ShippingCompanyController {

    @Autowired
    DefaultLog defaultLog;

    @Autowired
    ShippingCompanyService shippingCompanyService;

    private static final String API_V1 = "/shipping-company/v1/";

    @ApiOperation("Transportadora por Id")
    @GetMapping(value = "/v1/{id}")
    public ResponseEntity<ShippingCompanyResponse> getById(@Valid @PathVariable("id") Long id) {
        defaultLog.printLogExecutedEndpoint(HttpMethod.GET, API_V1);
        final ShippingCompanyResponse shippingCompanyResponse = shippingCompanyService.getById(id);
        return ResponseEntity.ok(shippingCompanyResponse);
    }

    @ApiOperation("Transportadora por CNPJ")
    @GetMapping(value = "/v1/document/{CNPJ}")
    public ResponseEntity<ShippingCompanyResponse> getByDocument(@Valid @PathVariable("CNPJ") String document) {
        defaultLog.printLogExecutedEndpoint(HttpMethod.GET, API_V1 + "document/");
        final ShippingCompanyResponse shippingCompanyResponse = shippingCompanyService.getByDocument(document);
        return ResponseEntity.ok(shippingCompanyResponse);
    }

    @ApiOperation("Transportadora listagem")
    @GetMapping(value = "/v1/")
    public ResponseEntity<Page<ShippingCompanyResponse>> getAll(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        return new ResponseEntity<>(shippingCompanyService.getAll(page, size), HttpStatus.OK);
    }

    @ApiOperation("Cadastro transportadora")
    @PostMapping(value = "/v1/")
    public ResponseEntity<ShippingCompanyResponse> create(@Valid @RequestBody ShippingCompanyRequest shippingCompanyRequest) {
        defaultLog.printLogExecutedEndpoint(HttpMethod.POST, API_V1);
        final ShippingCompanyResponse shippingCompanyResponse = shippingCompanyService.create(shippingCompanyRequest);
        return new ResponseEntity<>(shippingCompanyResponse, HttpStatus.CREATED);
    }

    @ApiOperation("Atualiza transportadora")
    @PutMapping(value = "/v1/{id}")
    public ResponseEntity<ShippingCompanyResponse> update(@Valid @PathVariable("id") Long id, @Valid @RequestBody ShippingCompanyRequest shippingCompanyRequest) {
        defaultLog.printLogExecutedEndpoint(HttpMethod.PUT, API_V1);
        final ShippingCompanyResponse shippingCompanyResponse = shippingCompanyService.update(id, shippingCompanyRequest);
        return new ResponseEntity<>(shippingCompanyResponse, HttpStatus.CREATED);
    }

    @ApiOperation("Remove transportadora")
    @DeleteMapping(value = "/v1/{id}")
    public ResponseEntity<DefaultResponse> delete(@Valid @PathVariable("id") Long id) {
        defaultLog.printLogExecutedEndpoint(HttpMethod.DELETE, API_V1);
        return new ResponseEntity<>(shippingCompanyService.delete(id), HttpStatus.ACCEPTED);
    }
}
