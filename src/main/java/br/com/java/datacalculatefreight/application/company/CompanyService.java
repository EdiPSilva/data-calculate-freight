package br.com.java.datacalculatefreight.application.company;

import br.com.java.datacalculatefreight.application.company.persistence.CompanyEntity;
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

import java.time.LocalDateTime;

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
        return CompanyResponse.from(getCompanyEntityById(id));
    }

    public CompanyResponse getByDocument(String document) {
        final var companyEntity = companyRepository.findByDocument(document);
        return CompanyResponse.from(companyEntity);
    }

    public CompanyResponse create(CompanyRequest companyRequest) {
        final var companyEntity = companyRepository.save(companyRequest.to());
        return CompanyResponse.from(companyEntity);
    }

    public CompanyResponse update(Long id, CompanyRequest companyRequest) {
        final var existingCompanyId = companyRepository.findCompanyEntityByDocument(companyRequest.getDocument());
        if (existingCompanyId == null) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.REGISTER_NOT_FOUND));
        }
        if (!existingCompanyId.equals(id)) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.REGISTER_NOT_FOUND_BY_ID, id.toString()));
        }
        //TODO - validar cnpj
        //TODO - validar cep
        var companyEntity = getCompanyEntityById(id);
        companyEntity.setName(companyRequest.getName());
        companyEntity.setDocument(companyRequest.getDocument());
        companyEntity.setPostalCode(companyRequest.getPostalCode());
        companyEntity.setActive(companyRequest.getActive());
        companyEntity.setDateUpdate(LocalDateTime.now());

        companyEntity = companyRepository.save(companyEntity);

        return CompanyResponse.from(companyEntity);
    }

    private CompanyEntity getCompanyEntityById(Long id) {
        genericValidations.validatevalidateNumberGreaterThanZero(id, MessageCodeEnum.INVALID_ID);
        return companyRepository.findById(id).orElseThrow(() -> new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.REGISTER_NOT_FOUND)));
    }
}
