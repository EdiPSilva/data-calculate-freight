package br.com.java.datacalculatefreight.application.rangeFreight.resources;

import br.com.java.datacalculatefreight.application.rangeFreight.persistence.RangeFreightEntity;
import br.com.java.datacalculatefreight.application.shippingCompany.resources.ShippingCompanyResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RangeFreightResponse {

    public RangeFreightResponse(final RangeFreightEntity rangeFreightEntity) {
        this.id = rangeFreightEntity.getId();
        this.shippingCompany = ShippingCompanyResponse.from(rangeFreightEntity.getShippingCompanyEntity());
        this.startValue = rangeFreightEntity.getStartValue();
        this.endValue = rangeFreightEntity.getEndValue();
        this.freightValue = rangeFreightEntity.getFreightValue();
        this.surplusValue = rangeFreightEntity.getSurplusValue();
        this.active = rangeFreightEntity.getActive();
        this.dateCreate = rangeFreightEntity.getDateCreate();
        this.dateUpdate = rangeFreightEntity.getDateUpdate();
    }

    @ApiModelProperty(notes = "Id do range de frete", example = "1", required = true)
    private Long id;

    @ApiModelProperty(notes = "Transportadora", required = true)
    private ShippingCompanyResponse shippingCompany;

    @ApiModelProperty(notes = "Valor inicial", example = "1.0", required = true)
    private Double startValue;

    @ApiModelProperty(notes = "Valor final", example = "1.0", required = true)
    private Double endValue;

    @ApiModelProperty(notes = "Valor frete", example = "1.0", required = true)
    private Double freightValue;

    @ApiModelProperty(notes = "Valor adicional", example = "1.0", required = true)
    private Double surplusValue;
    @ApiModelProperty(notes = "Status", example = "true", required = true)
    private Boolean active;

    @ApiModelProperty(notes = "Data de cadastro", example = "dd/MM/yyyy hh:mm:ss", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime dateCreate;

    @ApiModelProperty(notes = "Data de atualização", example = "dd/MM/yyyy hh:mm:ss", required = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime dateUpdate;

    public static RangeFreightResponse from(final RangeFreightEntity rangeFreightEntity) {
        return new RangeFreightResponse(rangeFreightEntity);
    }
}
