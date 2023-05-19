package br.com.java.datacalculatefreight.application.company.builders;

import br.com.java.datacalculatefreight.application.company.persistence.CompanyEntity;
import br.com.java.datacalculatefreight.application.company.resources.CompanyRequest;
import br.com.java.datacalculatefreight.utils.Fuctions;

import java.time.LocalDateTime;

public class CompanyEntityBuilder {

    private CompanyEntity companyEntity;

    private CompanyEntityBuilder () {

    }

    public static CompanyEntityBuilder getInstance() {
        return getInstance(1l);
    }

    public static CompanyEntityBuilder getInstance(final Long id) {
        final CompanyEntityBuilder builder = new CompanyEntityBuilder();
        builder.companyEntity = CompanyEntity.builder()
                .id(id)
                .name("Melissa e Renato Financeira ME")
                .document("33662514000161")
                .postalCode("13052110")
                .active(true)
                .dateCreate(Fuctions.getCreateDate())
                .build();
        return builder;
    }

    public static CompanyEntityBuilder getInstance(final CompanyRequest companyRequest) {
        final CompanyEntityBuilder builder = new CompanyEntityBuilder();
        builder.companyEntity = companyRequest.to();
        builder.companyEntity.setDateCreate(LocalDateTime.now());
        return builder;
    }

    public CompanyEntity getCompanyEntity() {
        return this.companyEntity;
    }
}
