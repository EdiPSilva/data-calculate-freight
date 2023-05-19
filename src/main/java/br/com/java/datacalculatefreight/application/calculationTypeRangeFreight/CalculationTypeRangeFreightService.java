package br.com.java.datacalculatefreight.application.calculationTypeRangeFreight;

import br.com.java.datacalculatefreight.application.calculationTypeRangeFreight.persistence.CalculationTypeEnum;
import br.com.java.datacalculatefreight.application.calculationTypeRangeFreight.persistence.CalculationTypeRangeFreightEntity;
import br.com.java.datacalculatefreight.application.calculationTypeRangeFreight.persistence.CalculationTypeRangeFreightRepository;
import br.com.java.datacalculatefreight.application.calculationTypeRangeFreight.resources.CalculationTypeRangeFreightRequest;
import br.com.java.datacalculatefreight.application.calculationTypeRangeFreight.resources.CalculationTypeRangeFreightResponse;
import br.com.java.datacalculatefreight.application.freightRoute.FreightRouteService;
import br.com.java.datacalculatefreight.application.freightRoute.persistence.FreightRouteEntity;
import br.com.java.datacalculatefreight.application.rangeFreight.RangeFreightService;
import br.com.java.datacalculatefreight.application.rangeFreight.persistence.RangeFreightEntity;
import br.com.java.datacalculatefreight.application.typeDelivery.TypeDeliveryService;
import br.com.java.datacalculatefreight.application.typeDelivery.persistence.TypeDeliveryEntity;
import br.com.java.datacalculatefreight.configuration.MessageCodeEnum;
import br.com.java.datacalculatefreight.configuration.MessageConfiguration;
import br.com.java.datacalculatefreight.exceptions.CustomException;
import br.com.java.datacalculatefreight.pageable.GenericPageable;
import br.com.java.datacalculatefreight.pageable.PageableDto;
import br.com.java.datacalculatefreight.utils.DefaultResponse;
import br.com.java.datacalculatefreight.utils.GenericValidations;
import br.com.java.datacalculatefreight.utils.StatusMessageEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CalculationTypeRangeFreightService {

    @Autowired
    private MessageConfiguration messageConfiguration;

    @Autowired
    private GenericValidations genericValidations;

    @Autowired
    private GenericPageable genericPageable;

    @Autowired
    private CalculationTypeRangeFreightRepository calculationTypeRangeFreightRepository;

    @Autowired
    private RangeFreightService rangeFreightService;

    @Autowired
    private FreightRouteService freightRouteService;

    @Autowired
    private TypeDeliveryService typeDeliveryService;

    public CalculationTypeRangeFreightResponse getById(Long id) {
        return CalculationTypeRangeFreightResponse.from(getCalculationTypeRangeFreightById(id));
    }

    private CalculationTypeRangeFreightEntity getCalculationTypeRangeFreightById(final Long id) {
        genericValidations.validatevalidateNumberGreaterThanZero(id, MessageCodeEnum.INVALID_ID);
        final Optional<CalculationTypeRangeFreightEntity> optionalCalculationTypeRangeFreightEntity = calculationTypeRangeFreightRepository.findById(id);
        if (optionalCalculationTypeRangeFreightEntity.isEmpty()) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.REGISTER_NOT_FOUND, "(tipo de cálculo faixa de frete)"), HttpStatus.NOT_FOUND);
        }
        return optionalCalculationTypeRangeFreightEntity.get();
    }

    public Page<CalculationTypeRangeFreightResponse> getAll(Integer page, Integer size, String sortBy, String sortDirection) {
        final Pageable pageable = genericPageable.buildPageable(new PageableDto(page, size, CalculationTypeRangeFreightEntity.class, sortBy, sortDirection));
        return new PageImpl<>(calculationTypeRangeFreightRepository.findAll(pageable).stream().map(CalculationTypeRangeFreightResponse::from).collect(Collectors.toList()));
    }

    public CalculationTypeRangeFreightResponse create(CalculationTypeRangeFreightRequest calculationTypeRangeFreightRequest) {
        final RangeFreightEntity rangeFreightEntity = rangeFreightService.getRangeFreightById(calculationTypeRangeFreightRequest.getRangeFreightId());
        final FreightRouteEntity freightRouteEntity = freightRouteService.getFreightRouteById(calculationTypeRangeFreightRequest.getFreightRouteId());
        final TypeDeliveryEntity typeDeliveryEntity = typeDeliveryService.getTypeDeliveryById(calculationTypeRangeFreightRequest.getTypeDelivery());
        final CalculationTypeEnum calculationType = genericValidations.getCalculationTypeEnum(calculationTypeRangeFreightRequest.getCalculationType());
        validateExistsCalculationTypeRangeFreight(calculationType, rangeFreightEntity.getId(), freightRouteEntity.getId(), typeDeliveryEntity.getId());
        final CalculationTypeRangeFreightEntity calculationTypeRangeFreightEntity = calculationTypeRangeFreightRequest.to(rangeFreightEntity, freightRouteEntity, typeDeliveryEntity);
        calculationTypeRangeFreightEntity.setCalculationType(calculationType);
        calculationTypeRangeFreightEntity.setDateCreate(LocalDateTime.now());
        return CalculationTypeRangeFreightResponse.from(calculationTypeRangeFreightRepository.save(calculationTypeRangeFreightEntity));
    }

    private void validateExistsCalculationTypeRangeFreight(final CalculationTypeEnum calculationType, final Long rangeFreightId, final Long freightRouteId, final Long typeDeliveryId) {
        final Long existsId = calculationTypeRangeFreightRepository.findAlreadyExistsCalculationTypeRangeFreight(calculationType, rangeFreightId, freightRouteId, typeDeliveryId);
        if (existsId != null) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.DOCUMENT_ALREADY_CADASTRE));
        }
    }

    public CalculationTypeRangeFreightResponse update(Long id, CalculationTypeRangeFreightRequest calculationTypeRangeFreightRequest) {
        final RangeFreightEntity rangeFreightEntity = rangeFreightService.getRangeFreightById(calculationTypeRangeFreightRequest.getRangeFreightId());
        final FreightRouteEntity freightRouteEntity = freightRouteService.getFreightRouteById(calculationTypeRangeFreightRequest.getFreightRouteId());
        final TypeDeliveryEntity typeDeliveryEntity = typeDeliveryService.getTypeDeliveryById(calculationTypeRangeFreightRequest.getTypeDelivery());
        final CalculationTypeEnum calculationType = genericValidations.getCalculationTypeEnum(calculationTypeRangeFreightRequest.getCalculationType());
        validateExistsCalculationTypeRangeFreight(calculationType, rangeFreightEntity.getId(), freightRouteEntity.getId(), typeDeliveryEntity.getId());
        final CalculationTypeRangeFreightEntity calculationTypeRangeFreightEntity = getCalculationTypeRangeFreightById(id);
        calculationTypeRangeFreightEntity.setCalculationType(calculationType);
        calculationTypeRangeFreightEntity.setRangeFreightEntity(rangeFreightEntity);
        calculationTypeRangeFreightEntity.setFreightRouteEntity(freightRouteEntity);
        calculationTypeRangeFreightEntity.setTypeDeliveryEntity(typeDeliveryEntity);
        calculationTypeRangeFreightEntity.setDateUpdate(LocalDateTime.now());
        return CalculationTypeRangeFreightResponse.from(calculationTypeRangeFreightRepository.save(calculationTypeRangeFreightEntity));
    }

    public DefaultResponse delete(Long id) {
        genericValidations.validatevalidateNumberGreaterThanZero(id, MessageCodeEnum.INVALID_ID);
        final CalculationTypeRangeFreightEntity calculationTypeRangeFreightEntity = getCalculationTypeRangeFreightById(id);
        final DefaultResponse defaultResponse = DefaultResponse.builder()
                .status(StatusMessageEnum.SUCCESS.getValue())
                .object(CalculationTypeRangeFreightResponse.from(calculationTypeRangeFreightEntity))
                .build();
        calculationTypeRangeFreightRepository.delete(calculationTypeRangeFreightEntity);
        return defaultResponse;
    }
}
