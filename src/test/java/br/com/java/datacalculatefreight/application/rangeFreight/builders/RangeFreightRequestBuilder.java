package br.com.java.datacalculatefreight.application.rangeFreight.builders;

import br.com.java.datacalculatefreight.application.rangeFreight.resources.RangeFreightRequest;

public class RangeFreightRequestBuilder {

    private RangeFreightRequest rangeFreightRequest;

    private RangeFreightRequestBuilder() {

    }

    public static RangeFreightRequestBuilder getInstance() {
        return getInstance(1.0, 1.0);
    }

    public static RangeFreightRequestBuilder getInstance(final Double startValue, final Double endValue) {
        final RangeFreightRequestBuilder builder = new RangeFreightRequestBuilder();
        builder.rangeFreightRequest = RangeFreightRequest.builder()
                .shippingCompanyId(1L)
                .startValue(startValue)
                .endValue(endValue)
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
