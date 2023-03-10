package br.com.java.datacalculatefreight.application.company.resource;

import br.com.java.datacalculatefreight.application.company.persistence.CompanyEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static br.com.java.datacalculatefreight.utils.MessageCode.NAME_NOT_BLANK;

@Getter
@Builder
public class CompanyRequest {

    @NotBlank(message = "{" + NAME_NOT_BLANK + "}")
    @Size(max = 125, message = "{" + NAME_NOT_BLANK + "}")
    @ApiModelProperty(notes = "Nome da empresa", example = "JP Comercio ME", required = true)
    private String name;

    @NotBlank
    @Size(max = 14, message = "Erro")
    @ApiModelProperty(notes = "Documento da empresa", example = "33662514000161", required = true)
    private String document;

    @NotBlank
    @Size(max = 8, message = "Erro")
    @ApiModelProperty(notes = "CEP da empresa", example = "13052110", required = true)
    private String postalCode;

    @NotNull
    @ApiModelProperty(notes = "Status da empresa", example = "true", required = true)
    private Boolean active;

    public CompanyEntity to() {
        return CompanyEntity.builder()
                .name(this.name)
                .document(this.document)
                .postalCode(this.postalCode)
                .active(this.active)
                .build();
    }
}
