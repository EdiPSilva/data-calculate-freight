package br.com.java.datacalculatefreight.application.company;

import br.com.java.datacalculatefreight.application.company.persistence.CompanyEntity;
import br.com.java.datacalculatefreight.application.company.persistence.CompanyRepository;
import br.com.java.datacalculatefreight.application.company.resources.CompanyRequest;
import br.com.java.datacalculatefreight.application.company.resources.CompanyResponse;
import br.com.java.datacalculatefreight.application.countryStates.persistence.CountryStatesEntity;
import br.com.java.datacalculatefreight.configuration.MessageCodeEnum;
import br.com.java.datacalculatefreight.configuration.MessageConfiguration;
import br.com.java.datacalculatefreight.exceptions.CustomException;
import br.com.java.datacalculatefreight.pageable.GenericPageable;
import br.com.java.datacalculatefreight.pageable.PageableDto;
import br.com.java.datacalculatefreight.utils.DefaultResponse;
import br.com.java.datacalculatefreight.utils.GenericValidations;
import br.com.java.datacalculatefreight.utils.StatusMessageEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    @Autowired
    MessageConfiguration messageConfiguration;

    @Autowired
    GenericValidations genericValidations;

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    GenericPageable genericPageable;

    public CompanyResponse getById(final Long id) {
        return CompanyResponse.from(getCompanyEntityById(id));
    }

    public CompanyResponse getByDocument(final String document) {
        genericValidations.cnpjIsValid(document);
        final CompanyEntity companyEntity = companyRepository.findByDocument(document);
        if (companyEntity == null) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.REGISTER_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        return CompanyResponse.from(companyEntity);
    }

    public Page<CompanyResponse> getAll(Integer page, Integer size, String sortBy, String sortDirection) {
        final Pageable pageable = genericPageable.buildPageable(new PageableDto(page, size, CompanyEntity.class, sortBy, sortDirection));
        return new PageImpl<>(companyRepository.findAll(pageable).stream().map(companyEntity -> CompanyResponse.from(companyEntity)).collect(Collectors.toList()));
    }

    public CompanyResponse create(final CompanyRequest companyRequest) {
        checkExistingCompanyByDocument(true, companyRequest.getDocument());
        CompanyEntity companyEntity = companyRequest.to();
        companyEntity.setDateCreate(LocalDateTime.now());
        companyEntity = companyRepository.save(companyEntity);
        return CompanyResponse.from(companyEntity);
    }

    public CompanyResponse update(final Long id, final CompanyRequest companyRequest) {
        genericValidations.validatevalidateNumberGreaterThanZero(id, MessageCodeEnum.INVALID_ID);
        final Long existingCompanyId = checkExistingCompanyByDocument(false, companyRequest.getDocument());
        if (!existingCompanyId.equals(id)) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.REGISTER_NOT_FOUND_BY_ID, id.toString()));
        }
        CompanyEntity companyEntity = getCompanyEntityById(id);
        companyEntity.setName(companyRequest.getName());
        companyEntity.setDocument(companyRequest.getDocument());
        companyEntity.setPostalCode(companyRequest.getPostalCode());
        companyEntity.setActive(companyRequest.getActive());
        companyEntity.setDateUpdate(LocalDateTime.now());

        companyEntity = companyRepository.save(companyEntity);

        return CompanyResponse.from(companyEntity);
    }

    private CompanyEntity getCompanyEntityById(final Long id) {
        genericValidations.validatevalidateNumberGreaterThanZero(id, MessageCodeEnum.INVALID_ID);
        final Optional<CompanyEntity> optionalCompanyEntity = companyRepository.findById(id);
        if (optionalCompanyEntity.isEmpty()) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.REGISTER_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        return optionalCompanyEntity.get();
    }

    private Long checkExistingCompanyByDocument(final boolean create, final String document) {
        genericValidations.cnpjIsValid(document);
        final Long existingCompanyId = companyRepository.findCompanyEntityByDocument(document);
        if (existingCompanyId == null && Boolean.FALSE.equals(create)) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.REGISTER_NOT_FOUND));
        } else if (existingCompanyId != null && Boolean.TRUE.equals(create)) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.DOCUMENT_ALREADY_CADASTRE));
        }
        return existingCompanyId;
    }

    public DefaultResponse delete(Long id) {
        genericValidations.validatevalidateNumberGreaterThanZero(id, MessageCodeEnum.INVALID_ID);
        final CompanyEntity companyEntity = getCompanyEntityById(id);
        final DefaultResponse defaultResponse = DefaultResponse.builder()
                .status(StatusMessageEnum.SUCCESS.getValue())
                .object(CompanyResponse.from(companyEntity))
                .build();
        companyRepository.delete(companyEntity);
        return defaultResponse;
    }
}
