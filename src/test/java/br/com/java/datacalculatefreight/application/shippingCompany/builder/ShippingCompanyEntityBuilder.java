package br.com.java.datacalculatefreight.application.shippingCompany.builder;

import br.com.java.datacalculatefreight.application.shippingCompany.persistence.ShippingCompanyEntity;
import br.com.java.datacalculatefreight.application.shippingCompany.resources.ShippingCompanyRequest;
import br.com.java.datacalculatefreight.utils.Fuctions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ShippingCompanyEntityBuilder {

    private ShippingCompanyEntity shippingCompanyEntity;

    private ShippingCompanyEntityBuilder() {

    }

    public static ShippingCompanyEntityBuilder getBasicShippingCompanyEntity() {
        return getBasicShippingCompanyEntity(1l);
    }

    public static ShippingCompanyEntityBuilder getBasicShippingCompanyEntity(final Long id) {
        final ShippingCompanyEntityBuilder builder = new ShippingCompanyEntityBuilder();
        builder.shippingCompanyEntity = ShippingCompanyEntity.builder()
                .id(id)
                .name("JP Log ME")
                .document("88449952000138")
                .active(true)
                .dateCreate(Fuctions.getCreateDate())
                .build();
        return builder;
    }

    public static ShippingCompanyEntityBuilder getShippingCompanyEntityByShippingCompanyRequest(final ShippingCompanyRequest shippingCompanyRequest) {
        final ShippingCompanyEntityBuilder builder = new ShippingCompanyEntityBuilder();
        builder.shippingCompanyEntity = shippingCompanyRequest.to();
        builder.shippingCompanyEntity.setDateCreate(LocalDateTime.now());
        return builder;
    }

    public ShippingCompanyEntity getShippingCompanyEntity() {
        return this.shippingCompanyEntity;
    }
}
