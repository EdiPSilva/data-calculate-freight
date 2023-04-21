package br.com.java.datacalculatefreight.application.calculationTypeRangeFreight.resources;

import br.com.java.datacalculatefreight.application.calculationTypeRangeFreight.persistence.CalculationTypeRangeFreightEntity;
import br.com.java.datacalculatefreight.application.freightRoute.resources.FreightRouteResponse;
import br.com.java.datacalculatefreight.application.rangeFreight.resources.RangeFreightResponse;
import br.com.java.datacalculatefreight.application.typeDelivery.resources.TypeDeliveryResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CalculationTypeRangeFreightResponse {

    private CalculationTypeRangeFreightResponse(final CalculationTypeRangeFreightEntity calculationTypeRangeFreightEntity) {
       this.id = calculationTypeRangeFreightEntity.getId();
       this.calculationType = calculationTypeRangeFreightEntity.getCalculationType().getValue();
       this.rangeFreight = RangeFreightResponse.from(calculationTypeRangeFreightEntity.getRangeFreightEntity());
       this.freightRoute = FreightRouteResponse.from(calculationTypeRangeFreightEntity.getFreightRouteEntity());
       this.typeDelivery = TypeDeliveryResponse.from(calculationTypeRangeFreightEntity.getTypeDeliveryEntity());
       this.dateCreate = calculationTypeRangeFreightEntity.getDateCreate();
       this.dateUpdate = calculationTypeRangeFreightEntity.getDateUpdate();
    }

    @ApiModelProperty(notes = "Id", example = "1", required = true)
    private Long id;

    @ApiModelProperty(notes = "Tipo de cálculo", example = "CUBAGE", required = true)
    private String calculationType;

    @ApiModelProperty(notes = "Range de frete", required = true)
    private RangeFreightResponse rangeFreight;

    @ApiModelProperty(notes = "Rota de frete", required = true)
    private FreightRouteResponse freightRoute;

    @ApiModelProperty(notes = "Tipo de entraga", required = true)
    private TypeDeliveryResponse typeDelivery;

    @ApiModelProperty(notes = "Data de cadastro", example = "dd/MM/yyyy hh:mm:ss", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime dateCreate;

    @ApiModelProperty(notes = "Data de atualização", example = "dd/MM/yyyy hh:mm:ss", required = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime dateUpdate;

    public static CalculationTypeRangeFreightResponse from(final CalculationTypeRangeFreightEntity calculationTypeRangeFreightEntity) {
        return new CalculationTypeRangeFreightResponse(calculationTypeRangeFreightEntity);
    }
}