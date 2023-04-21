package br.com.java.datacalculatefreight.application.rangeFreight;

import br.com.java.datacalculatefreight.application.rangeFreight.persistence.RangeFreightEntity;
import br.com.java.datacalculatefreight.application.rangeFreight.persistence.RangeFreightRepository;
import br.com.java.datacalculatefreight.application.rangeFreight.resources.RangeFreightRequest;
import br.com.java.datacalculatefreight.application.rangeFreight.resources.RangeFreightResponse;
import br.com.java.datacalculatefreight.application.shippingCompany.ShippingCompanyService;
import br.com.java.datacalculatefreight.application.shippingCompany.persistence.ShippingCompanyEntity;
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
public class RangeFreightService {

    @Autowired
    private MessageConfiguration messageConfiguration;

    @Autowired
    private GenericValidations genericValidations;

    @Autowired
    private GenericPageable genericPageable;

    @Autowired
    private RangeFreightRepository rangeFreightRepository;

    @Autowired
    private ShippingCompanyService shippingCompanyService;

    public RangeFreightResponse getById(Long id) {
        return RangeFreightResponse.from(getRangeFreightById(id));
    }

    public RangeFreightEntity getRangeFreightById(final Long id) {
        genericValidations.validatevalidateNumberGreaterThanZero(id, MessageCodeEnum.INVALID_ID);
        final Optional<RangeFreightEntity> optionalRangeFreightEntity = rangeFreightRepository.findById(id);
        if (optionalRangeFreightEntity.isEmpty()) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.REGISTER_NOT_FOUND, "(range de frete)"), HttpStatus.NOT_FOUND);
        }
        return optionalRangeFreightEntity.get();
    }

    public Page<RangeFreightResponse> getAll(Integer page, Integer size, String sortBy, String sortDirection) {
        final Pageable pageable = genericPageable.buildPageable(new PageableDto(page, size, RangeFreightEntity.class, sortBy, sortDirection));
        return new PageImpl<>(rangeFreightRepository.findAll(pageable).stream().map(RangeFreightResponse::from).collect(Collectors.toList()));
    }

    public RangeFreightResponse create(final RangeFreightRequest rangeFreightRequest) {
        final ShippingCompanyEntity shippingCompanyEntity = shippingCompanyService.getShippingCompanyById(rangeFreightRequest.getShippingCompanyId());
        final RangeFreightEntity rangeFreightEntity = rangeFreightRequest.to(shippingCompanyEntity);
        rangeFreightEntity.setDateCreate(LocalDateTime.now());
        return RangeFreightResponse.from(rangeFreightRepository.save(rangeFreightEntity));
    }

    public RangeFreightResponse update(Long id, RangeFreightRequest rangeFreightRequest) {
        genericValidations.validatevalidateNumberGreaterThanZero(id, MessageCodeEnum.INVALID_ID);
        final ShippingCompanyEntity shippingCompanyEntity = shippingCompanyService.getShippingCompanyById(rangeFreightRequest.getShippingCompanyId());
        final RangeFreightEntity rangeFreightEntity = getRangeFreightById(id);
        rangeFreightEntity.setShippingCompanyEntity(shippingCompanyEntity);
        rangeFreightEntity.setStartValue(rangeFreightRequest.getStartValue());
        rangeFreightEntity.setEndValue(rangeFreightRequest.getEndValue());
        rangeFreightEntity.setFreightValue(rangeFreightRequest.getFreightValue());
        rangeFreightEntity.setSurplusValue(rangeFreightRequest.getSurplusValue());
        rangeFreightEntity.setActive(rangeFreightRequest.getActive());
        rangeFreightEntity.setDateUpdate(LocalDateTime.now());
        return RangeFreightResponse.from(rangeFreightRepository.save(rangeFreightEntity));
    }

    public DefaultResponse delete(Long id) {
        genericValidations.validatevalidateNumberGreaterThanZero(id, MessageCodeEnum.INVALID_ID);
        final RangeFreightEntity rangeFreightEntity = getRangeFreightById(id);
        final DefaultResponse defaultResponse = DefaultResponse.builder()
                .status(StatusMessageEnum.SUCCESS.getValue())
                .object(RangeFreightResponse.from(rangeFreightEntity))
                .build();
        rangeFreightRepository.delete(rangeFreightEntity);
        return defaultResponse;
    }
}
