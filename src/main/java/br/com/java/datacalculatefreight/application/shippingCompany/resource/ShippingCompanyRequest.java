package br.com.java.datacalculatefreight.application.shippingCompany.resource;

import br.com.java.datacalculatefreight.application.shippingCompany.persistence.ShippingCompanyEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Builder
public class ShippingCompanyRequest {

    @NotBlank(message = "Não deve estar em branco")
    @Size(max = 125)
    @ApiModelProperty(notes = "Nome da transportadora", example = "JP Log ME", required = true)
    private String name;

    @NotBlank
    @Size(min = 14, max = 14)
    @ApiModelProperty(notes = "Documento da transportadora", example = "88449952000138", required = true)
    private String document;

    @NotNull
    @ApiModelProperty(notes = "Status da transportadora", example = "true", required = true)
    private Boolean active;

    public ShippingCompanyEntity to() {
        return ShippingCompanyEntity.builder()
                .name(this.name)
                .document(this.document)
                .active(this.active)
                .build();
    }
}