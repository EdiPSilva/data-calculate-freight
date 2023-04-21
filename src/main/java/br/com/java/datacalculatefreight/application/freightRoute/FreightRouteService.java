package br.com.java.datacalculatefreight.application.freightRoute;

import br.com.java.datacalculatefreight.application.freightRoute.persistence.FreightRouteEntity;
import br.com.java.datacalculatefreight.application.freightRoute.persistence.FreightRouteRepository;
import br.com.java.datacalculatefreight.application.freightRoute.resources.FreightRouteRequest;
import br.com.java.datacalculatefreight.application.freightRoute.resources.FreightRouteResponse;
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
public class FreightRouteService {

    @Autowired
    MessageConfiguration messageConfiguration;

    @Autowired
    GenericValidations genericValidations;

    @Autowired
    GenericPageable genericPageable;

    @Autowired
    FreightRouteRepository freightRouteRepository;

    public FreightRouteResponse getById(final Long id) {
        return FreightRouteResponse.from(getFreightRouteById(id));
    }

    public FreightRouteEntity getFreightRouteById(final Long id) {
        genericValidations.validatevalidateNumberGreaterThanZero(id, MessageCodeEnum.INVALID_ID);
        final Optional<FreightRouteEntity> optionalFreightRouteEntity = freightRouteRepository.findById(id);
        if (optionalFreightRouteEntity.isEmpty()) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.REGISTER_NOT_FOUND, "(rota de frete)"), HttpStatus.NOT_FOUND);
        }
        return optionalFreightRouteEntity.get();
    }

    public FreightRouteResponse getByPostalCode(final String postalCode, final String column) {
        genericValidations.validatePostCode(postalCode, MessageCodeEnum.INVALID_POST_CODE);
        final FreightRouteEntity freightRouteEntity = getPostalCodeByColumn(postalCode, column);
        if (freightRouteEntity == null) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.REGISTER_NOT_FOUND, "(rota de frete)"), HttpStatus.NOT_FOUND);
        }
        return FreightRouteResponse.from(freightRouteEntity);
    }

    private FreightRouteEntity getPostalCodeByColumn(final String postalCode, final String column) {
        if (column.equals("start")) {
            return freightRouteRepository.findByStartPostalCode(postalCode);
        }
        return freightRouteRepository.findByEndPostalCode(postalCode);
    }

    public Page<FreightRouteResponse> getAll(Integer page, Integer size, String sortBy, String sortDirection) {
        final Pageable pageable = genericPageable.buildPageable(new PageableDto(page, size, FreightRouteEntity.class, sortBy, sortDirection));
        return new PageImpl<>(freightRouteRepository.findAll(pageable).stream().map(FreightRouteResponse::from).collect(Collectors.toList()));
    }

    public FreightRouteResponse create(FreightRouteRequest freightRouteRequest) {
        checkExistingPostalCode(null, freightRouteRequest.getStartPostalCode(), freightRouteRequest.getEndPostalCode());
        final FreightRouteEntity freightRouteEntity = freightRouteRequest.to();
        freightRouteEntity.setStateCode(genericValidations.getStatesByCode(freightRouteRequest.getStateCode()));
        freightRouteEntity.setDateCreate(LocalDateTime.now());
        return FreightRouteResponse.from(freightRouteRepository.save(freightRouteEntity));
    }

    private void checkExistingPostalCode(final Long id, final String startPostalCode, final String endPostalCode) {
        if (startPostalCode.equals(endPostalCode)) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.START_AND_END_POSTAL_CODE_EQUALS));
        }
        validatePostalCodeBeforeCreate(id, startPostalCode);
        validatePostalCodeBeforeCreate(id, endPostalCode);
    }

    private void validatePostalCodeBeforeCreate(final Long id, final String postalCode) {
        genericValidations.validatePostCode(postalCode, MessageCodeEnum.INVALID_POST_CODE);
        final Long idByStartPostalCode = freightRouteRepository.findFreightRouteEntityByStartPostalCode(postalCode);
        final Long idByEndPostalCode = freightRouteRepository.findFreightRouteEntityByEndPostalCode(postalCode);
        compareIds(idByStartPostalCode, id, MessageCodeEnum.START_POSTAL_CODE_ALREADY_CADASTRE, postalCode);
        compareIds(idByEndPostalCode, id, MessageCodeEnum.END_POSTAL_CODE_ALREADY_CADASTRE, postalCode);
    }

    private void compareIds(final Long idFound, final Long idReceived, final MessageCodeEnum messageCodeEnum, final String postalCode) {
        if (idReceived != null) {
            if (idFound != null && !idFound.equals(idReceived)) {
                throw new CustomException(messageConfiguration.getMessageByCode(messageCodeEnum, postalCode));//TODO - trocar a mensagem de erro
            }
        } else if (idFound != null) {
            throw new CustomException(messageConfiguration.getMessageByCode(messageCodeEnum, postalCode));
        }
    }

    public FreightRouteResponse update(final Long id, final FreightRouteRequest freightRouteRequest) {
        genericValidations.validatevalidateNumberGreaterThanZero(id, MessageCodeEnum.INVALID_ID);
        checkExistingPostalCode(id, freightRouteRequest.getStartPostalCode(), freightRouteRequest.getEndPostalCode());
        final FreightRouteEntity freightRouteEntity = getFreightRouteById(id);
        freightRouteEntity.setStartPostalCode(freightRouteRequest.getStartPostalCode());
        freightRouteEntity.setEndPostalCode(freightRouteRequest.getEndPostalCode());
        freightRouteEntity.setDeliveryDays(freightRouteRequest.getDeliveryDays());
        freightRouteEntity.setDevolutionDays(freightRouteRequest.getDevolutionDays());
        freightRouteEntity.setActive(freightRouteRequest.getActive());
        freightRouteEntity.setDateUpdate(LocalDateTime.now());
        freightRouteEntity.setStateCode(genericValidations.getStatesByCode(freightRouteRequest.getStateCode()));
        return FreightRouteResponse.from(freightRouteRepository.save(freightRouteEntity));
    }

    public DefaultResponse delete(Long id) {//TODO - validar quando já houver algum registro associado
        genericValidations.validatevalidateNumberGreaterThanZero(id, MessageCodeEnum.INVALID_ID);
        final FreightRouteEntity freightRouteEntity = getFreightRouteById(id);
        final DefaultResponse defaultResponse = DefaultResponse.builder()
                .status(StatusMessageEnum.SUCCESS.getValue())
                .object(FreightRouteResponse.from(freightRouteEntity))
                .build();
        freightRouteRepository.delete(freightRouteEntity);
        return defaultResponse;
    }
}
