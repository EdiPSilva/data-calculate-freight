package br.com.java.datacalculatefreight.application.calculationFreight.builders;

import br.com.java.datacalculatefreight.application.calculationFreight.persistence.CalculationFreightEntity;
import br.com.java.datacalculatefreight.utils.Fuctions;

import java.time.LocalDate;

public class CalculationFreightEntityBuilder {

    private CalculationFreightEntity calculationFreightEntity;

    private CalculationFreightEntityBuilder() {

    }

    public static CalculationFreightEntityBuilder getBasicCalculationFreightEntity(final Long id) {
        final CalculationFreightEntityBuilder builder = new CalculationFreightEntityBuilder();
        builder.calculationFreightEntity = CalculationFreightEntity.builder()
                .id(id)
                .delivaryDay(LocalDate.now())
                .destinyPostalCode("14000001")
                .width(3.0)
                .height(4.0)
                .length(3.0)
                .cubage(36.0)
                .weight(3.0)
                .freightValue(2.20)
                .dateCreate(Fuctions.getCreateDate())
                .build();
        return builder;
    }

    public CalculationFreightEntity getCalculationFreightEntity() {
        return this.calculationFreightEntity;
    }
}
