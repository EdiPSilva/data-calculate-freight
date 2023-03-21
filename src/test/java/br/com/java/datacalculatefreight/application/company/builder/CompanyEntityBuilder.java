package br.com.java.datacalculatefreight.application.company.builder;

import br.com.java.datacalculatefreight.application.company.persistence.CompanyEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CompanyEntityBuilder {

    private CompanyEntity companyEntity;

    private CompanyEntityBuilder () {

    }

    public static CompanyEntityBuilder getBasicCompanyEntity() {
        return getBasicCompanyEntity(1l);
    }

    public static CompanyEntityBuilder getBasicCompanyEntity(final Long id) {
        final var stringDate = "2023-03-20 20:00";
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        final LocalDateTime createDate = LocalDateTime.parse(stringDate, formatter);

        final var builder = new CompanyEntityBuilder();
        builder.companyEntity = CompanyEntity.builder()
                .id(id)
                .name("Melissa e Renato Financeira ME")
                .document("33662514000161")
                .postalCode("13052110")
                .active(true)
                .dateCreate(createDate)
                .build();
        return builder;
    }

    public CompanyEntity getCompanyEntity() {
        return this.companyEntity;
    }
}
