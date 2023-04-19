package br.com.java.datacalculatefreight.application.freightRoute.resources;

import br.com.java.datacalculatefreight.application.freightRoute.persistence.FreightRouteEntity;
import br.com.java.datacalculatefreight.application.freightRoute.persistence.StatesEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FreightRouteResponse {

    public FreightRouteResponse(FreightRouteEntity freightRouteEntity) {
        this.id = freightRouteEntity.getId();
        this.startPostalCode = freightRouteEntity.getStartPostalCode();
        this.endPostalCode = freightRouteEntity.getEndPostalCode();
        this.deliveryDays = freightRouteEntity.getDeliveryDays();
        this.devolutionDays = freightRouteEntity.getDevolutionDays();
        this.stateCode = freightRouteEntity.getStateCode();
        this.active = freightRouteEntity.getActive();
        this.dateCreate = freightRouteEntity.getDateCreate();
        this.dateUpdate = freightRouteEntity.getDateUpdate();
    }

    @ApiModelProperty(notes = "Id da rota de frete", example = "1", required = true)
    private Long id;

    @ApiModelProperty(notes = "CEP inicial", example = "14000000", required = true)
    private String startPostalCode;

    @ApiModelProperty(notes = "CEP final", example = "14000010", required = true)
    private String endPostalCode;

    @ApiModelProperty(notes = "Dias de entrega", example = "10", required = true)
    private Long deliveryDays;

    @ApiModelProperty(notes = "Dias de devolução", example = "15", required = true)
    private Long devolutionDays;

    @ApiModelProperty(notes = "UF", example = "SP", required = true)
    private StatesEnum stateCode;

    @ApiModelProperty(notes = "Status", example = "true", required = true)
    private Boolean active;

    @ApiModelProperty(notes = "Data de cadastro", example = "dd/MM/yyyy hh:mm:ss", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime dateCreate;

    @ApiModelProperty(notes = "Data de atualização", example = "dd/MM/yyyy hh:mm:ss", required = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime dateUpdate;

    public static FreightRouteResponse from(final FreightRouteEntity freightRouteEntity) {
        return new FreightRouteResponse(freightRouteEntity);
    }
}
