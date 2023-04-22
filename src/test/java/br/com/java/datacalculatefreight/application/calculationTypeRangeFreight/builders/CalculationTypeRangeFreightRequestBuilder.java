package br.com.java.datacalculatefreight.application.calculationTypeRangeFreight.builders;

import br.com.java.datacalculatefreight.application.calculationTypeRangeFreight.resources.CalculationTypeRangeFreightRequest;

public class CalculationTypeRangeFreightRequestBuilder {

    private CalculationTypeRangeFreightRequest calculationTypeRangeFreightRequest;

    private CalculationTypeRangeFreightRequestBuilder() {

    }

    public static CalculationTypeRangeFreightRequestBuilder getInstance() {
        final CalculationTypeRangeFreightRequestBuilder builder = new CalculationTypeRangeFreightRequestBuilder();
        builder.calculationTypeRangeFreightRequest = CalculationTypeRangeFreightRequest.builder()
                .calculationType("CUBAGE")
                .rangeFreightId(1L)
                .freightRouteId(1L)
                .typeDelivery(1L)
                .build();
        return builder;
    }

    public CalculationTypeRangeFreightRequest getCalculationTypeRangeFreightRequest() {
        return this.calculationTypeRangeFreightRequest;
    }
}
