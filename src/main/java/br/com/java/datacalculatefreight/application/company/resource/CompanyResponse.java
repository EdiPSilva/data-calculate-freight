package br.com.java.datacalculatefreight.application.company.resource;

import br.com.java.datacalculatefreight.application.company.persistence.CompanyEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CompanyResponse {

    public CompanyResponse(CompanyEntity companyEntity) {
        this.id = companyEntity.getId();
        this.name = companyEntity.getName();
        this.document = companyEntity.getDocument();
        this.postalCode = companyEntity.getPostalCode();
        this.active = companyEntity.getActive();
    }

    @ApiModelProperty(notes = "Id da empresa", example = "1", required = true)
    private Long id;
    @ApiModelProperty(notes = "Nome da empresa", example = "JP Comercio ME", required = true)
    private String name;
    @ApiModelProperty(notes = "CNPJ da empresa", example = "33662514000161", required = true)
    private String document;
    @ApiModelProperty(notes = "CEP da empresa", example = "13052110", required = true)
    private String postalCode;
    @ApiModelProperty(notes = "Status da empresa", example = "true", required = true)
    private Boolean active;

    public static CompanyResponse from(CompanyEntity companyEntity) {
        return new CompanyResponse(companyEntity);
    }
}
