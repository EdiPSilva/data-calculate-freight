package br.com.java.datacalculatefreight.application.calculationTypeRangeFreight.resources;

import br.com.java.datacalculatefreight.application.calculationTypeRangeFreight.persistence.CalculationTypeRangeFreightEntity;
import br.com.java.datacalculatefreight.application.freightRoute.persistence.FreightRouteEntity;
import br.com.java.datacalculatefreight.application.rangeFreight.persistence.RangeFreightEntity;
import br.com.java.datacalculatefreight.application.typeDelivery.persistence.TypeDeliveryEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class CalculationTypeRangeFreightRequest {

    @NotBlank(message = "Não deve estar em branco")
    @ApiModelProperty(notes = "Tipo de calculo", example = "CUBAGE", required = true)
    private String calculationType;

    @Min(value = 1L, message = "Valor mínimo é 1")
    @ApiModelProperty(notes = "Id range frete", example = "1", required = true)
    private Long rangeFreightId;

    @Min(value = 1L, message = "Valor mínimo é 1")
    @ApiModelProperty(notes = "Id rota frete", example = "1", required = true)
    private Long freightRouteId;

    @Min(value = 1L, message = "Valor mínimo é 1")
    @ApiModelProperty(notes = "Id tipo entrega", example = "1", required = true)
    private Long typeDelivery;

    public CalculationTypeRangeFreightEntity to(final RangeFreightEntity rangeFreightEntity, final FreightRouteEntity freightRouteEntity, final TypeDeliveryEntity typeDeliveryEntity) {
        return CalculationTypeRangeFreightEntity.builder()
                .rangeFreightEntity(rangeFreightEntity)
                .freightRouteEntity(freightRouteEntity)
                .typeDeliveryEntity(typeDeliveryEntity)
                .build();
    }
}
