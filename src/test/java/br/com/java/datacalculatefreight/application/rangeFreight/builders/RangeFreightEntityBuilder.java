package br.com.java.datacalculatefreight.application.rangeFreight.builders;

import br.com.java.datacalculatefreight.application.rangeFreight.persistence.RangeFreightEntity;
import br.com.java.datacalculatefreight.application.rangeFreight.resources.RangeFreightRequest;
import br.com.java.datacalculatefreight.application.shippingCompany.builders.ShippingCompanyEntityBuilder;
import br.com.java.datacalculatefreight.application.shippingCompany.persistence.ShippingCompanyEntity;
import br.com.java.datacalculatefreight.utils.Fuctions;

import java.time.LocalDateTime;

public class RangeFreightEntityBuilder {

    private RangeFreightEntity rangeFreightEntity;

    private RangeFreightEntityBuilder() {

    }

    public static RangeFreightEntityBuilder getBasicRangeFreightEntity() {
        return getBasicRangeFreightEntity(1L);
    }

    public static RangeFreightEntityBuilder getBasicRangeFreightEntity(final Long id) {
        final RangeFreightEntityBuilder builder = new RangeFreightEntityBuilder();
        builder.rangeFreightEntity = RangeFreightEntity.builder()
                .id(id)
                .shippingCompanyEntity(ShippingCompanyEntityBuilder.getBasicShippingCompanyEntity().getShippingCompanyEntity())
                .startValue(1.0)
                .endValue(1.0)
                .freightValue(1.0)
                .surplusValue(1.0)
                .active(true)
                .dateCreate(Fuctions.getCreateDate())
                .build();
        return builder;
    }

    public static RangeFreightEntityBuilder getBasicRangeFreightEntityByRangeFreightRequest(final RangeFreightRequest rangeFreightRequest, final ShippingCompanyEntity shippingCompanyEntity) {
        final RangeFreightEntityBuilder builder = new RangeFreightEntityBuilder();
        builder.rangeFreightEntity = rangeFreightRequest.to(shippingCompanyEntity);
        builder.rangeFreightEntity.setDateCreate(LocalDateTime.now());
        builder.rangeFreightEntity.setId(1L);
        return builder;
    }

    public RangeFreightEntity getRangeFreightEntity() {
        return this.rangeFreightEntity;
    }
}
