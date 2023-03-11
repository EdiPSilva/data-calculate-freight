package br.com.java.datacalculatefreight.application.company;

import br.com.java.datacalculatefreight.application.company.persistence.CompanyRepository;
import br.com.java.datacalculatefreight.application.company.resource.CompanyRequest;
import br.com.java.datacalculatefreight.application.company.resource.CompanyResponse;
import br.com.java.datacalculatefreight.configuration.MessageCodeEnum;
import br.com.java.datacalculatefreight.configuration.MessageConfiguration;
import br.com.java.datacalculatefreight.exceptions.CustomException;
import br.com.java.datacalculatefreight.utils.GenericValidations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CompanyService {

    @Autowired
    MessageConfiguration messageConfiguration;

    @Autowired
    GenericValidations genericValidations;

    @Autowired
    CompanyRepository companyRepository;

    public CompanyResponse getById(Long id) {
        genericValidations.validatevalidateNumberGreaterThanZero(id, MessageCodeEnum.INVALID_ID);
        final var companyEntity = companyRepository.findById(id).orElseThrow(() -> new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.REGISTER_NOT_FOUND)));
        return CompanyResponse.from(companyEntity);
    }

    public CompanyResponse getByCnpj(String cnpj) {
        final var companyEntity = companyRepository.findByDocument(cnpj);
        return CompanyResponse.from(companyEntity);
    }

    public CompanyResponse create(CompanyRequest companyRequest) {
        final var companyEntity = companyRepository.save(companyRequest.to());
        return CompanyResponse.from(companyEntity);
    }
}
