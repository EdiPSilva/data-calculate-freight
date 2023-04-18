package br.com.java.datacalculatefreight.application.calculationFreight.builders;

import br.com.java.datacalculatefreight.application.calculationFreight.persistence.CalculationFreightEntity;
import br.com.java.datacalculatefreight.utils.Fuctions;

public class CalculationFreightEntityBuilder {

    private CalculationFreightEntity calculationFreightEntity;

    private CalculationFreightEntityBuilder() {

    }

    public static CalculationFreightEntityBuilder getBasicCalculationFreightEntity(final Long id) {
        final CalculationFreightEntityBuilder builder = new CalculationFreightEntityBuilder();
        builder.calculationFreightEntity = CalculationFreightEntity.builder()
                .id(id)
                .senderPostalCode("13459395")
                .destinyPostalCode("09760739")
                .width(1L)
                .height(1L)
                .length(1L)
                .cubage(1L)
                .weight(1D)
                .freightValue(1d)
                .dateCreate(Fuctions.getCreateDate())
                .build();
        return builder;
    }

    public CalculationFreightEntity getCalculationFreightEntity() {
        return this.calculationFreightEntity;
    }
}
