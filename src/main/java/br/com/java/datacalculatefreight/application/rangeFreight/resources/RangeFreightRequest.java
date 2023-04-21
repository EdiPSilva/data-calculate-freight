package br.com.java.datacalculatefreight.application.rangeFreight.resources;

import br.com.java.datacalculatefreight.application.rangeFreight.persistence.RangeFreightEntity;
import br.com.java.datacalculatefreight.application.shippingCompany.persistence.ShippingCompanyEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Builder
public class RangeFreightRequest {

    @Min(value = 1L, message = "Valor mínimo é 1")
    @ApiModelProperty(notes = "Id da transportadora", example = "1", required = true)
    private Long shippingCompanyId;

    @Min(value = 0, message = "Valor mínimo é 0")
    @ApiModelProperty(notes = "Valor inicial", example = "0.10", required = true)
    private Double startValue;

    @Min(value = 0, message = "Valor mínimo é 0")
    @ApiModelProperty(notes = "Valor final", example = "0.10", required = true)
    private Double endValue;

    @Min(value = 0, message = "Valor mínimo é 0")
    @ApiModelProperty(notes = "Valor frete", example = "0.10", required = true)
    private Double freightValue;

    @Min(value = 0, message = "Valor mínimo é 0")
    @ApiModelProperty(notes = "Valor adicional do frete", example = "0.10", required = true)
    private Double surplusValue;

    @NotNull
    @ApiModelProperty(notes = "Status", example = "true", required = true)
    private Boolean active;

    public RangeFreightEntity to() {
        return to(null);
    }

    public RangeFreightEntity to(final ShippingCompanyEntity shippingCompanyEntity) {
        return RangeFreightEntity.builder()
                .shippingCompanyEntity(shippingCompanyEntity)
                .startValue(this.startValue)
                .endValue(this.endValue)
                .freightValue(this.freightValue)
                .surplusValue(this.surplusValue)
                .active(this.active)
                .build();
    }
}
