package br.com.java.datacalculatefreight.application.company.resource;

import br.com.java.datacalculatefreight.application.company.CompanyService;
import br.com.java.datacalculatefreight.utils.DefaultLog;
import br.com.java.datacalculatefreight.utils.DefaultResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/company", produces = MediaType.APPLICATION_JSON_VALUE)
public class CompanyController {

    @Autowired
    DefaultLog defaultLog;

    @Autowired
    CompanyService companyService;

    private static final String API_V1 = "/company/v1/";

    @ApiOperation("Empresa por Id")
    @GetMapping(value = "/v1/{id}")
    public ResponseEntity<CompanyResponse> getById(@Valid @PathVariable("id") Long id) {
        defaultLog.printLogExecutedEndpoint(HttpMethod.GET, API_V1);
        final var companyResponse = companyService.getById(id);
        return ResponseEntity.ok(companyResponse);
    }

    @ApiOperation("Empresa por CNPJ")
    @GetMapping(value = "/v1/document/{CNPJ}")
    public ResponseEntity<CompanyResponse> getByDocument(@Valid @PathVariable("CNPJ") String document) {
        defaultLog.printLogExecutedEndpoint(HttpMethod.GET, API_V1 + "/document/");
        final var companyResponse = companyService.getByDocument(document);
        return ResponseEntity.ok(companyResponse);
    }

    @ApiOperation("Cadastro empresa")
    @PostMapping(value = "/v1/")
    public ResponseEntity<CompanyResponse> create(@Valid @RequestBody CompanyRequest companyRequest) {
        defaultLog.printLogExecutedEndpoint(HttpMethod.POST, API_V1);
        final var companyResponse = companyService.create(companyRequest);
        return new ResponseEntity<>(companyResponse, HttpStatus.CREATED);
    }

    @ApiOperation("Atualiza empresa")
    @PutMapping(value = "/v1/{id}")
    public ResponseEntity<CompanyResponse> update(@Valid @PathVariable("id") Long id, @Valid @RequestBody CompanyRequest companyRequest) {
        defaultLog.printLogExecutedEndpoint(HttpMethod.PUT, API_V1);
        final var companyResponse = companyService.update(id, companyRequest);
        return new ResponseEntity<>(companyResponse, HttpStatus.CREATED);
    }

    @ApiOperation("Remove empresa")
    @DeleteMapping(value = "/v1/{id}")
    public ResponseEntity<DefaultResponse> delete(@Valid @PathVariable("id") Long id) {
        return new ResponseEntity<>(companyService.delete(id), HttpStatus.ACCEPTED);
    }
}
