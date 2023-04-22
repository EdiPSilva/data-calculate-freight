package br.com.java.datacalculatefreight.application.calculationTypeRangeFreight.builders;

import br.com.java.datacalculatefreight.application.calculationTypeRangeFreight.persistence.CalculationTypeEnum;
import br.com.java.datacalculatefreight.application.calculationTypeRangeFreight.persistence.CalculationTypeRangeFreightEntity;
import br.com.java.datacalculatefreight.application.calculationTypeRangeFreight.resources.CalculationTypeRangeFreightRequest;
import br.com.java.datacalculatefreight.application.freightRoute.builders.FreightRouteEntityBuilder;
import br.com.java.datacalculatefreight.application.rangeFreight.builders.RangeFreightEntityBuilder;
import br.com.java.datacalculatefreight.application.typeDelivery.builders.TypeDeliveryEntityBuilder;
import br.com.java.datacalculatefreight.utils.Fuctions;

public class CalculationTypeRangeFreightEntityBuilder {

    private CalculationTypeRangeFreightEntity calculationTypeRangeFreightEntity;

    private CalculationTypeRangeFreightEntityBuilder() {

    }

    public static CalculationTypeRangeFreightEntityBuilder getInstance() {
        return getInstance(1L);
    }

    public static CalculationTypeRangeFreightEntityBuilder getInstance(final Long id) {
        final CalculationTypeRangeFreightEntityBuilder builder = new CalculationTypeRangeFreightEntityBuilder();
        builder.calculationTypeRangeFreightEntity = CalculationTypeRangeFreightEntity.builder()
                .id(id)
                .calculationType(CalculationTypeEnum.CUBAGE)
                .rangeFreightEntity(RangeFreightEntityBuilder.getBasicRangeFreightEntity().getRangeFreightEntity())
                .typeDeliveryEntity(TypeDeliveryEntityBuilder.getBasicTypeDeliveryEntity().getTypeDeliveryEntity())
                .freightRouteEntity(FreightRouteEntityBuilder.getBasicFreightRouteEntity().getFreightRouteEntity())
                .dateCreate(Fuctions.getCreateDate())
                .build();
        return builder;
    }

    public static CalculationTypeRangeFreightEntityBuilder getInstance(final CalculationTypeRangeFreightRequest calculationTypeRangeFreightRequest) {

        final CalculationTypeRangeFreightEntityBuilder builder = new CalculationTypeRangeFreightEntityBuilder();
        builder.calculationTypeRangeFreightEntity = CalculationTypeRangeFreightEntity.builder()
                .id(1L)
                .calculationType(getCalculationTypeEnum(calculationTypeRangeFreightRequest.getCalculationType()))
                .rangeFreightEntity(RangeFreightEntityBuilder.getBasicRangeFreightEntity(calculationTypeRangeFreightRequest.getRangeFreightId()).getRangeFreightEntity())
                .typeDeliveryEntity(TypeDeliveryEntityBuilder.getBasicTypeDeliveryEntity(calculationTypeRangeFreightRequest.getTypeDelivery()).getTypeDeliveryEntity())
                .freightRouteEntity(FreightRouteEntityBuilder.getBasicFreightRouteEntity(calculationTypeRangeFreightRequest.getFreightRouteId()).getFreightRouteEntity())
                .dateCreate(Fuctions.getCreateDate())
                .build();
        return builder;
    }

    private static CalculationTypeEnum getCalculationTypeEnum(final String value) {
        if (value.toUpperCase().equals(CalculationTypeEnum.WEIGHT.getValue())) {
            return CalculationTypeEnum.WEIGHT;
        }
        return CalculationTypeEnum.CUBAGE;
    }

    public CalculationTypeRangeFreightEntity getCalculationTypeRangeFreightEntity() {
        return this.calculationTypeRangeFreightEntity;
    }
}
