package br.com.java.datacalculatefreight.application.freightRoute.builders;

import br.com.java.datacalculatefreight.application.freightRoute.resources.FreightRouteRequest;

public class FreightRouteRequestBuilder {

    private FreightRouteRequest freightRouteRequest;

    private FreightRouteRequestBuilder() {

    }

    public static FreightRouteRequestBuilder getBasicFreightRouteRequest() {
        return getBasicFreightRouteRequest(false);
    }

    public static FreightRouteRequestBuilder getBasicFreightRouteRequest(final boolean equals) {
        final String startPostalCode = "14000000";
        final String endPostalCode = equals ? startPostalCode : "14000020";
        final FreightRouteRequestBuilder builder = new FreightRouteRequestBuilder();
        builder.freightRouteRequest = FreightRouteRequest.builder()
                .startPostalCode(startPostalCode)
                .endPostalCode(endPostalCode)
                .deliveryDays(10L)
                .devolutionDays(15L)
                .active(true)
                .stateCode("SP")
                .build();
        return builder;
    }

    public FreightRouteRequest getFreightRouteRequest() {
        return this.freightRouteRequest;
    }
}
