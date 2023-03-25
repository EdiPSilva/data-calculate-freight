package br.com.java.datacalculatefreight.application.typeDelivery.resources;

import br.com.java.datacalculatefreight.application.typeDelivery.persistence.TypeDeliveryEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Builder
public class TypeDeliveryRequest {

    @NotBlank(message = "Não deve estar em branco")
    @Size(max = 125)
    @ApiModelProperty(notes = "Tipo entrega", example = "EXPRESS", required = true)
    private String type;

    @NotNull
    @ApiModelProperty(notes = "Status", example = "true", required = true)
    private Boolean active;

    public TypeDeliveryEntity to() {
        return TypeDeliveryEntity.builder()
                .type(this.type)
                .active(this.active)
                .build();
    }
}
