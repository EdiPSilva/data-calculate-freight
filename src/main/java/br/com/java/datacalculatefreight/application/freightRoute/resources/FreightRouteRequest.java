package br.com.java.datacalculatefreight.application.freightRoute.resources;

import br.com.java.datacalculatefreight.application.freightRoute.persistence.FreightRouteEntity;
import br.com.java.datacalculatefreight.utils.GenericValidations;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.*;

@Getter
@Builder
public class FreightRouteRequest {

    @NotBlank(message = "Não deve estar em branco")
    @Size(min = 8, max = 8)
    @ApiModelProperty(notes = "CEP inicial", example = "14000000", required = true)
    private String startPostalCode;

    @NotBlank(message = "Não deve estar em branco")
    @Size(min = 8, max = 8)
    @ApiModelProperty(notes = "CEP final", example = "14000010", required = true)
    private String endPostalCode;

    @Min(value = 1L, message = "Valor mínimo é 1")
    @ApiModelProperty(notes = "Dias de entrega", example = "10", required = true)
    private Long deliveryDays;

    @Min(value = 1L, message = "Valor mínimo é 1")
    @ApiModelProperty(notes = "Dias de devolução", example = "15", required = true)
    private Long devolutionDays;

    @NotNull
    @ApiModelProperty(notes = "Status", example = "true", required = true)
    private Boolean active;

    @NotBlank(message = "Não deve estar em branco")
    @Size(min = 2, max = 2)
    @ApiModelProperty(notes = "UF", example = "SP", required = true)
    private String stateCode;

    public FreightRouteEntity to() {
        return FreightRouteEntity.builder()
                .startPostalCode(this.startPostalCode)
                .endPostalCode(this.endPostalCode)
                .deliveryDays(this.deliveryDays)
                .devolutionDays(this.devolutionDays)
                .active(this.active)
                .build();
    }
}
