package br.com.java.datacalculatefreight.application.calculationFreight;

import br.com.java.datacalculatefreight.application.calculationFreight.persistence.CalculationFreightEntity;
import br.com.java.datacalculatefreight.application.calculationFreight.persistence.CalculationFreightRepository;
import br.com.java.datacalculatefreight.application.calculationFreight.resources.CalculationFreightResponse;
import br.com.java.datacalculatefreight.configuration.MessageCodeEnum;
import br.com.java.datacalculatefreight.configuration.MessageConfiguration;
import br.com.java.datacalculatefreight.exceptions.CustomException;
import br.com.java.datacalculatefreight.pageable.GenericPageable;
import br.com.java.datacalculatefreight.pageable.PageableDto;
import br.com.java.datacalculatefreight.utils.GenericValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CalculationFreightService {

    @Autowired
    MessageConfiguration messageConfiguration;

    @Autowired
    GenericValidations genericValidations;

    @Autowired
    GenericPageable genericPageable;

    @Autowired
    CalculationFreightRepository calculationFreightRepository;

    public CalculationFreightResponse getById(Long id) {
        return CalculationFreightResponse.from(getCalculationFreightEntityById(id));
    }

    private CalculationFreightEntity getCalculationFreightEntityById(final Long id) {
        genericValidations.validatevalidateNumberGreaterThanZero(id, MessageCodeEnum.INVALID_ID);
        final Optional<CalculationFreightEntity> optionalCalculationFreightEntity = calculationFreightRepository.findById(id);
        if (optionalCalculationFreightEntity.isEmpty()) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.REGISTER_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        return optionalCalculationFreightEntity.get();
    }

    public Page<CalculationFreightResponse> getAll(Integer page, Integer size, String sortBy, String sortDirection) {
        final Pageable pageable = genericPageable.buildPageable(new PageableDto(page, size, CalculationFreightEntity.class, sortBy, sortDirection));
        return new PageImpl<>(calculationFreightRepository.findAll(pageable).stream().map(CalculationFreightResponse::from).collect(Collectors.toList()));
    }
}