package br.com.java.datacalculatefreight.application.countryStates;

import br.com.java.datacalculatefreight.application.countryStates.persistence.CountryStatesEntity;
import br.com.java.datacalculatefreight.application.countryStates.persistence.CountryStatesRepository;
import br.com.java.datacalculatefreight.application.countryStates.resources.CountryStatesResponse;
import br.com.java.datacalculatefreight.configuration.MessageCodeEnum;
import br.com.java.datacalculatefreight.configuration.MessageConfiguration;
import br.com.java.datacalculatefreight.exceptions.CustomException;
import br.com.java.datacalculatefreight.pageable.GenericPageable;
import br.com.java.datacalculatefreight.utils.GenericValidations;
import br.com.java.datacalculatefreight.pageable.PageableDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryStatesService {

    @Autowired
    MessageConfiguration messageConfiguration;

    @Autowired
    GenericValidations genericValidations;

    @Autowired
    CountryStatesRepository countryStatesRepository;

    @Autowired
    GenericPageable genericPageable;

    public CountryStatesResponse getById(Long id) {
        return CountryStatesResponse.from(getCountryStatesById(id));
    }

    private CountryStatesEntity getCountryStatesById(Long id) {
        genericValidations.validatevalidateNumberGreaterThanZero(id, MessageCodeEnum.INVALID_ID);
        final Optional<CountryStatesEntity> optionalCountryStatesEntity = countryStatesRepository.findById(id);
        if (optionalCountryStatesEntity.isEmpty()) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.REGISTER_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        return optionalCountryStatesEntity.get();
    }

    public CountryStatesResponse getByStateCode(final String stateCode) {
        if (stateCode == null || stateCode.trim().isEmpty() || stateCode.trim().length() != 2) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.INVALID_STATE_ABBREVIATION), HttpStatus.NOT_FOUND);
        }
        final CountryStatesEntity countryStatesEntity = countryStatesRepository.findByStateCode(stateCode.toUpperCase().trim());
        if (countryStatesEntity == null) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.REGISTER_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        return CountryStatesResponse.from(countryStatesEntity);
    }

    public Page<CountryStatesResponse> getAll(Integer page, Integer size, String sortBy, String sortDirection) {
        final Pageable pageable = genericPageable.buildPageable(new PageableDto(page, size, CountryStatesEntity.class, sortBy, sortDirection));
        return new PageImpl<>(countryStatesRepository.findAll(pageable).stream().map(countryStatesEntity -> CountryStatesResponse.from(countryStatesEntity)).collect(Collectors.toList()));
    }
}
