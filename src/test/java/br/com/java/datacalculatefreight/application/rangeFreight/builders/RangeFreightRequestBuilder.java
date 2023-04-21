package br.com.java.datacalculatefreight.application.rangeFreight.builders;

import br.com.java.datacalculatefreight.application.rangeFreight.resources.RangeFreightRequest;

public class RangeFreightRequestBuilder {

    private RangeFreightRequest rangeFreightRequest;

    private RangeFreightRequestBuilder() {

    }

    public static RangeFreightRequestBuilder getBasicRangeFreightRequest() {
        final RangeFreightRequestBuilder builder = new RangeFreightRequestBuilder();
        builder.rangeFreightRequest = RangeFreightRequest.builder()
                .shippingCompanyId(1L)
                .startValue(1.0)
                .endValue(1.0)
                .freightValue(1.0)
                .surplusValue(1.0)
                .active(true)
                .build();
        return builder;
    }

    public RangeFreightRequest getRangeFreightRequest(){
        return this.rangeFreightRequest;
    }
}
