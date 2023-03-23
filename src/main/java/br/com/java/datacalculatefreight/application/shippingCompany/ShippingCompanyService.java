package br.com.java.datacalculatefreight.application.shippingCompany;

import br.com.java.datacalculatefreight.application.company.persistence.CompanyEntity;
import br.com.java.datacalculatefreight.application.company.resource.CompanyRequest;
import br.com.java.datacalculatefreight.application.company.resource.CompanyResponse;
import br.com.java.datacalculatefreight.application.shippingCompany.persistence.ShippingCompanyEntity;
import br.com.java.datacalculatefreight.application.shippingCompany.persistence.ShippingCompanyRepository;
import br.com.java.datacalculatefreight.application.shippingCompany.resource.ShippingCompanyResponse;
import br.com.java.datacalculatefreight.application.shippingCompany.resource.ShippingCompanyRequest;
import br.com.java.datacalculatefreight.configuration.MessageCodeEnum;
import br.com.java.datacalculatefreight.configuration.MessageConfiguration;
import br.com.java.datacalculatefreight.exceptions.CustomException;
import br.com.java.datacalculatefreight.utils.DefaultResponse;
import br.com.java.datacalculatefreight.utils.GenericValidations;
import br.com.java.datacalculatefreight.utils.StatusMessageEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShippingCompanyService {

    @Autowired
    MessageConfiguration messageConfiguration;

    @Autowired
    GenericValidations genericValidations;

    @Autowired
    ShippingCompanyRepository shippingCompanyRepository;

    public ShippingCompanyResponse getById(Long id) {
        return ShippingCompanyResponse.from(getShippingCompanyById(id));
    }

    public ShippingCompanyResponse getByDocument(String document) {
        genericValidations.cnpjIsValid(document);
        final ShippingCompanyEntity shippingCompanyEntity = shippingCompanyRepository.findByDocument(document);
        if (shippingCompanyEntity == null) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.REGISTER_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        return ShippingCompanyResponse.from(shippingCompanyEntity);
    }

    public Page<ShippingCompanyResponse> getAll(Integer page, Integer size) {
        if (page == null || page < 0) page = 0;
        if (size == null || size < 1 || size > 10) size = 10;
        final PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "name");
        return new PageImpl<>(shippingCompanyRepository.findAll().stream().map(shippingCompanyEntity -> ShippingCompanyResponse.from(shippingCompanyEntity)).collect(Collectors.toList()), pageRequest, size);
    }

    public ShippingCompanyResponse create(final ShippingCompanyRequest shippingCompanyRequest) {
        checkExistingCompanyByDocument(true, shippingCompanyRequest.getDocument());
        ShippingCompanyEntity shippingCompanyEntity = shippingCompanyRequest.to();
        shippingCompanyEntity.setDateCreate(LocalDateTime.now());
        shippingCompanyEntity = shippingCompanyRepository.save(shippingCompanyEntity);
        return ShippingCompanyResponse.from(shippingCompanyEntity);
    }

    public ShippingCompanyResponse update(final Long id, final ShippingCompanyRequest shippingCompanyRequest) {
        final Long existingCompanyId = checkExistingCompanyByDocument(false, shippingCompanyRequest.getDocument());
        if (!existingCompanyId.equals(id)) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.REGISTER_NOT_FOUND_BY_ID, id.toString()));
        }
        ShippingCompanyEntity shippingCompanyEntity = getShippingCompanyById(id);
        shippingCompanyEntity.setName(shippingCompanyRequest.getName());
        shippingCompanyEntity.setDocument(shippingCompanyRequest.getDocument());
        shippingCompanyEntity.setActive(shippingCompanyRequest.getActive());
        shippingCompanyEntity.setDateUpdate(LocalDateTime.now());

        shippingCompanyEntity = shippingCompanyRepository.save(shippingCompanyEntity);

        return ShippingCompanyResponse.from(shippingCompanyEntity);
    }

    private ShippingCompanyEntity getShippingCompanyById(Long id) {
        genericValidations.validatevalidateNumberGreaterThanZero(id, MessageCodeEnum.INVALID_ID);
        final Optional<ShippingCompanyEntity> optionalShippingCompanyEntity = shippingCompanyRepository.findById(id);
        if (optionalShippingCompanyEntity.isEmpty()) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.REGISTER_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        return optionalShippingCompanyEntity.get();
    }

    private Long checkExistingCompanyByDocument(final boolean create, final String document) {
        genericValidations.cnpjIsValid(document);
        final Long existingShippingCompanyId = shippingCompanyRepository.findShippingCompanyEntityByDocument(document);
        if (existingShippingCompanyId == null && Boolean.FALSE.equals(create)) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.REGISTER_NOT_FOUND));
        } else if (existingShippingCompanyId != null && Boolean.TRUE.equals(create)) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.DOCUMENT_ALREADY_CADASTRE));
        }
        return existingShippingCompanyId;
    }

    public DefaultResponse delete(Long id) {
        genericValidations.validatevalidateNumberGreaterThanZero(id, MessageCodeEnum.INVALID_ID);
        final ShippingCompanyEntity shippingCompanyEntity = getShippingCompanyById(id);
        final DefaultResponse defaultResponse = DefaultResponse.builder()
                .status(StatusMessageEnum.SUCCESS.getValue())
                .object(ShippingCompanyResponse.from(shippingCompanyEntity))
                .build();
        shippingCompanyRepository.delete(shippingCompanyEntity);
        return defaultResponse;
    }
}
