package br.com.java.datacalculatefreight.application.company.resource;

import br.com.java.datacalculatefreight.application.company.CompanyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/company", produces = MediaType.APPLICATION_JSON_VALUE)
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @ApiOperation("Buscar empresa por Id")
    @GetMapping(value = "/v1/{id}")
    public ResponseEntity<CompanyResponse> getById(@Valid @PathVariable("id") Long id) {
        final var companyResponse = companyService.getById(id);
        return ResponseEntity.ok(companyResponse);
    }

    @ApiOperation("Buscar empresa por CNPJ")
    @GetMapping(value = "/v1/document/{CNPJ}")
    public ResponseEntity<CompanyResponse> getByCnpj(@Valid @PathVariable("CNPJ") String cnpj) {
        final var companyResponse = companyService.getByCnpj(cnpj);
        return ResponseEntity.ok(companyResponse);
    }

    @ApiOperation("Cadastro de empresa")
    @PostMapping(value = "/v1/")
    public ResponseEntity<CompanyResponse> create(@Valid @RequestBody CompanyRequest companyRequest) {
        final var companyResponse = companyService.create(companyRequest);
        return new ResponseEntity<>(companyResponse, HttpStatus.CREATED);
    }
}
