package br.com.java.datacalculatefreight.application.typeDelivery.builders;

import br.com.java.datacalculatefreight.application.typeDelivery.persistence.TypeDeliveryEntity;
import br.com.java.datacalculatefreight.application.typeDelivery.resources.TypeDeliveryRequest;
import br.com.java.datacalculatefreight.utils.Fuctions;

import java.time.LocalDateTime;

public class TypeDeliveryEntityBuilder {

    private TypeDeliveryEntity typeDeliveryEntity;

    private TypeDeliveryEntityBuilder() {

    }

    public static TypeDeliveryEntityBuilder getInstance() {
        return getInstance(1l);
    }

    public static TypeDeliveryEntityBuilder getInstance(final Long id) {
        final TypeDeliveryEntityBuilder builder = new TypeDeliveryEntityBuilder();
        builder.typeDeliveryEntity = TypeDeliveryEntity.builder()
                .id(id)
                .type("EXPRESS")
                .active(true)
                .dateCreate(Fuctions.getCreateDate())
                .build();
        return builder;
    }

    public static TypeDeliveryEntityBuilder getInstance(final TypeDeliveryRequest typeDeliveryRequest) {
        final TypeDeliveryEntityBuilder builder = new TypeDeliveryEntityBuilder();
        builder.typeDeliveryEntity = typeDeliveryRequest.to();
        builder.typeDeliveryEntity.setDateCreate(LocalDateTime.now());
        return builder;
    }

    public TypeDeliveryEntity getTypeDeliveryEntity() {
        return this.typeDeliveryEntity;
    }

}
