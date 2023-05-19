package br.com.java.datacalculatefreight.application.calculationFreight.resources;

import br.com.java.datacalculatefreight.application.calculationFreight.persistence.CalculationFreightEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CalculationFreightResponse {

    public CalculationFreightResponse(final CalculationFreightEntity calculationFreightEntity) {
        this.id = calculationFreightEntity.getId();
        this.destinyPostalCode = calculationFreightEntity.getDestinyPostalCode();
        this.width = calculationFreightEntity.getWidth();
        this.height = calculationFreightEntity.getHeight();
        this.length = calculationFreightEntity.getLength();
        this.cubage = calculationFreightEntity.getCubage();
        this.weight = calculationFreightEntity.getWeight();
        this.freightValue = calculationFreightEntity.getFreightValue();
        this.dateCreate = calculationFreightEntity.getDateCreate();
        this.dateUpdate = calculationFreightEntity.getDateUpdate();
    }

    @ApiModelProperty(notes = "Id do cálculo de frete", example = "1", required = true)
    private Long id;

    @ApiModelProperty(notes = "Destino cep", example = "1", required = true)
    private String destinyPostalCode;

    @ApiModelProperty(notes = "Largura", example = "1", required = true)
    private Double width;

    @ApiModelProperty(notes = "Altura", example = "1", required = true)
    private Double height;

    @ApiModelProperty(notes = "Comprimento", example = "1", required = true)
    private Double length;

    @ApiModelProperty(notes = "Cubagem", example = "1", required = true)
    private Double cubage;

    @ApiModelProperty(notes = "Peso", example = "1", required = true)
    private Double weight;

    @ApiModelProperty(notes = "Valor do frete", example = "1", required = true)
    private Double freightValue;

    @ApiModelProperty(notes = "Data de cadastro", example = "dd/MM/yyyy hh:mm:ss", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime dateCreate;

    @ApiModelProperty(notes = "Data de atualização", example = "dd/MM/yyyy hh:mm:ss", required = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime dateUpdate;

    public static CalculationFreightResponse from(final CalculationFreightEntity calculationFreightEntity) {
        return new CalculationFreightResponse(calculationFreightEntity);
    }
}
