package br.com.java.datacalculatefreight.application.orders;

import br.com.java.datacalculatefreight.application.calculationFreight.CalculationFreightService;
import br.com.java.datacalculatefreight.application.company.CompanyService;
import br.com.java.datacalculatefreight.application.company.persistence.CompanyEntity;
import br.com.java.datacalculatefreight.application.orders.persistence.OrdersEntity;
import br.com.java.datacalculatefreight.application.orders.persistence.OrdersRepository;
import br.com.java.datacalculatefreight.application.orders.resources.OrdersResponse;
import br.com.java.datacalculatefreight.configuration.MessageCodeEnum;
import br.com.java.datacalculatefreight.configuration.MessageConfiguration;
import br.com.java.datacalculatefreight.exceptions.CustomException;
import br.com.java.datacalculatefreight.pageable.GenericPageable;
import br.com.java.datacalculatefreight.pageable.PageableDto;
import br.com.java.datacalculatefreight.utils.GenericValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private MessageConfiguration messageConfiguration;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CalculationFreightService calculationFreightService;

    @Autowired
    private GenericPageable genericPageable;

    @Autowired
    private GenericValidations genericValidations;

    public OrdersResponse getById(Long id) {
        return OrdersResponse.from(getOrdersEntityById(id));
    }

    private OrdersEntity getOrdersEntityById(final Long id) {
        genericValidations.validatevalidateNumberGreaterThanZero(id, MessageCodeEnum.INVALID_ID);
        final Optional<OrdersEntity> optionalOrdersEntity = ordersRepository.findById(id);
        if (optionalOrdersEntity.isEmpty()) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.REGISTER_NOT_FOUND, "(pedido)"), HttpStatus.NOT_FOUND);
        }
        return optionalOrdersEntity.get();
    }

    public OrdersResponse getByOrder(String orderNumber, Long companyId) {
        genericValidations.validatevalidateNumberGreaterThanZero(companyId, MessageCodeEnum.INVALID_ID);
        final OrdersEntity ordersEntity = ordersRepository.findOrdersByNumberAndCompany(orderNumber, companyService.getCompanyEntityById(companyId).getId());
        if (ordersEntity == null) {
            throw new CustomException(messageConfiguration.getMessageByCode(MessageCodeEnum.REGISTER_NOT_FOUND, "(pedido)"), HttpStatus.NOT_FOUND);
        }
        return OrdersResponse.from(ordersEntity);
    }

    public Page<OrdersResponse> getAll(Integer page, Integer size, String sortBy, String sortDirection) {
        final Pageable pageable = genericPageable.buildPageable(new PageableDto(page, size, CompanyEntity.class, sortBy, sortDirection));
        return new PageImpl<>(ordersRepository.findAll(pageable).stream().map(OrdersResponse::from).collect(Collectors.toList()));
    }
}
