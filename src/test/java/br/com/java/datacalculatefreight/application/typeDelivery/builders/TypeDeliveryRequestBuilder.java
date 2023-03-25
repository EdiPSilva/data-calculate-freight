package br.com.java.datacalculatefreight.application.typeDelivery.builders;

import br.com.java.datacalculatefreight.application.typeDelivery.resources.TypeDeliveryRequest;

public class TypeDeliveryRequestBuilder {

    private TypeDeliveryRequest typeDeliveryRequest;

    private TypeDeliveryRequestBuilder() {

    }

    public static TypeDeliveryRequestBuilder getBasicTypeDeliveryRequest() {
        final TypeDeliveryRequestBuilder builder = new TypeDeliveryRequestBuilder();
        builder.typeDeliveryRequest = TypeDeliveryRequest.builder()
                .type("EXPRESS")
                .active(true)
                .build();
        return builder;
    }

    public TypeDeliveryRequest getTypeDeliveryRequest() {
        return this.typeDeliveryRequest;
    }
}
