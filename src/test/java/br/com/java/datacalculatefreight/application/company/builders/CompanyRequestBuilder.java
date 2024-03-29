package br.com.java.datacalculatefreight.application.company.builders;

import br.com.java.datacalculatefreight.application.company.resources.CompanyRequest;

public class CompanyRequestBuilder {

    private CompanyRequest companyRequest;

    private CompanyRequestBuilder() {

    }

    public static CompanyRequestBuilder getInstance() {
        final CompanyRequestBuilder builder = new CompanyRequestBuilder();
        builder.companyRequest = CompanyRequest.builder()
                .name("Melissa e Renato Financeira ME")
                .document("33662514000161")
                .postalCode("13052110")
                .active(true)
                .build();
        return builder;
    }

    public CompanyRequest getCompanyRequest() {
        return this.companyRequest;
    }
}
