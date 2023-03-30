package br.com.java.datacalculatefreight.application.countryStates.builders;

import br.com.java.datacalculatefreight.application.countryStates.persistence.CountryStatesEntity;
import br.com.java.datacalculatefreight.utils.Fuctions;

public class CountryStatesEntityBuilder {

    private CountryStatesEntity countryStatesEntity;

    private CountryStatesEntityBuilder() {

    }

    public static CountryStatesEntityBuilder getBasicCountryStatesEntity() {
        return getBasicCountryStatesEntity(1l);
    }

    public static CountryStatesEntityBuilder getBasicCountryStatesEntity(final Long id) {
        final CountryStatesEntityBuilder builder = new CountryStatesEntityBuilder();
        builder.countryStatesEntity = CountryStatesEntity.builder()
                .id(id)
                .state("Acre")
                .stateCode("AC")
                .dateCreate(Fuctions.getCreateDate("2023-03-28 00:00"))
                .build();
        return builder;
    }

    public CountryStatesEntity getCountryStatesEntity() {
        return this.countryStatesEntity;
    }
}
