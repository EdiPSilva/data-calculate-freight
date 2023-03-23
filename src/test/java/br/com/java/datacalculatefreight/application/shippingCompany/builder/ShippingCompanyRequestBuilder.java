package br.com.java.datacalculatefreight.application.shippingCompany.builder;

import br.com.java.datacalculatefreight.application.shippingCompany.resources.ShippingCompanyRequest;

public class ShippingCompanyRequestBuilder {

    private ShippingCompanyRequest shippingCompanyRequest;

    private ShippingCompanyRequestBuilder() {

    }

    public static ShippingCompanyRequestBuilder getBasicShippingCompanyRequest() {
        final ShippingCompanyRequestBuilder builder = new ShippingCompanyRequestBuilder();
        builder.shippingCompanyRequest = ShippingCompanyRequest.builder()
                .name("JP Log ME")
                .document("88449952000138")
                .active(true)
                .build();
        return builder;
    }

    public ShippingCompanyRequest getShippingCompanyRequest() {
        return this.shippingCompanyRequest;
    }
}
