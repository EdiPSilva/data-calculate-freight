package br.com.java.datacalculatefreight.application.typeDelivery;

import br.com.java.datacalculatefreight.application.typeDelivery.persistence.TypeDeliveryEntity;
import br.com.java.datacalculatefreight.application.typeDelivery.persistence.TypeDeliveryRepository;
import br.com.java.datacalculatefreight.application.typeDelivery.resources.TypeDeliveryRequest;
import br.com.java.datacalculatefreight.application.typeDelivery.resources.TypeDeliveryResponse;
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
public class TypeDeliveryService {

    @Autowired
    MessageConfiguration messageConfiguration;

    @Autowired
    GenericValidations genericValidations;

    @Autowired
    TypeDeliveryRepository typeDeliveryRepository;

    @Autowired
    GenericPageable genericPageable;

    public TypeDeliveryResponse getById(final Long id) {
        return TypeDeliveryResponse.from(getTypeDeliveryById(id));
    }

    private TypeDeliveryEntity getTypeDeliveryById(final Long id) {
        genericValidations.validatevalidateNumberGreaterThanZero(id, MessageCodeEnum.INVALID_ID);
        final Optional<TypeDeliveryEntity> optionalTypeDeliveryEntity = typeDeliveryRepository.findById(id);
        if (optionalTypeDeliveryEntity.isEmpty()) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.REGISTER_NOT_FOUND, "(tipo de entrega)"), HttpStatus.NOT_FOUND);
        }
        return optionalTypeDeliveryEntity.get();
    }

    public TypeDeliveryResponse getByType(String type) {
        final TypeDeliveryEntity typeDeliveryEntity = typeDeliveryRepository.findByType(formatType(type));
        if (typeDeliveryEntity == null) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.REGISTER_NOT_FOUND, "(tipo de entrega)"), HttpStatus.NOT_FOUND);
        }
        return TypeDeliveryResponse.from(typeDeliveryEntity);
    }

    private String formatType(final String value) {
        return value.toUpperCase().trim();
    }

    public Page<TypeDeliveryResponse> getAll(Integer page, Integer size, String sortBy, String sortDirection) {
        final Pageable pageable = genericPageable.buildPageable(new PageableDto(page, size, TypeDeliveryEntity.class, sortBy, sortDirection));
        return new PageImpl<>(typeDeliveryRepository.findAll(pageable).stream().map(typeDeliveryEntity -> TypeDeliveryResponse.from(typeDeliveryEntity)).collect(Collectors.toList()));
    }

    public TypeDeliveryResponse create(final TypeDeliveryRequest typeDeliveryRequest) {
        checkExistingTypeDeliveryByType(true, typeDeliveryRequest.getType());
        final TypeDeliveryEntity typeDeliveryEntity = typeDeliveryRequest.to();
        typeDeliveryEntity.setDateCreate(LocalDateTime.now());
        return TypeDeliveryResponse.from(typeDeliveryRepository.save(typeDeliveryEntity));
    }

    private Long checkExistingTypeDeliveryByType(final boolean create, final String type) {
        final Long existingId = typeDeliveryRepository.findTypeDeliveryEntityByType(formatType(type));
        if (existingId == null && Boolean.FALSE.equals(create)) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.REGISTER_NOT_FOUND));
        } else if (existingId != null && Boolean.TRUE.equals(create)) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.REGISTER_ALREADY_CADASTRE));
        }
        return existingId;
    }

    public TypeDeliveryResponse update(Long id, TypeDeliveryRequest typeDeliveryRequest) {
        genericValidations.validatevalidateNumberGreaterThanZero(id, MessageCodeEnum.INVALID_ID);
        final Long existingId = checkExistingTypeDeliveryByType(false, typeDeliveryRequest.getType());
        if (!existingId.equals(id)) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.REGISTER_NOT_FOUND_BY_ID, id.toString()));
        }
        final TypeDeliveryEntity typeDeliveryEntity = getTypeDeliveryById(id);
        typeDeliveryEntity.setType(typeDeliveryRequest.getType());
        typeDeliveryEntity.setActive(typeDeliveryRequest.getActive());
        typeDeliveryEntity.setDateUpdate(LocalDateTime.now());
        return TypeDeliveryResponse.from(typeDeliveryRepository.save(typeDeliveryEntity));
    }

    public DefaultResponse delete(Long id) {
        genericValidations.validatevalidateNumberGreaterThanZero(id, MessageCodeEnum.INVALID_ID);
        final TypeDeliveryEntity typeDeliveryEntity = getTypeDeliveryById(id);
        final DefaultResponse defaultResponse = DefaultResponse.builder()
                .status(StatusMessageEnum.SUCCESS.getValue())
                .object(TypeDeliveryResponse.from(typeDeliveryEntity))
                .build();
        return defaultResponse;
    }
}
