package br.com.java.datacalculatefreight.application.orders;

import br.com.java.datacalculatefreight.application.calculationFreight.CalculationFreightService;
import br.com.java.datacalculatefreight.application.company.CompanyService;
import br.com.java.datacalculatefreight.application.company.builders.CompanyEntityBuilder;
import br.com.java.datacalculatefreight.application.company.persistence.CompanyEntity;
import br.com.java.datacalculatefreight.application.orders.builders.OrdersEntityBuilder;
import br.com.java.datacalculatefreight.application.orders.persistence.OrdersEntity;
import br.com.java.datacalculatefreight.application.orders.persistence.OrdersRepository;
import br.com.java.datacalculatefreight.application.orders.resources.OrdersResponse;
import br.com.java.datacalculatefreight.configuration.MessageConfiguration;
import br.com.java.datacalculatefreight.exceptions.CustomException;
import br.com.java.datacalculatefreight.pageable.GenericPageable;
import br.com.java.datacalculatefreight.utils.GenericValidations;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrdersServiceTest {

    @InjectMocks
    private OrdersService ordersService;

    @Mock
    private OrdersRepository ordersRepository;

    @Mock
    private MessageConfiguration messageConfiguration;

    @Mock
    private CompanyService companyService;

    @Mock
    private CalculationFreightService calculationFreightService;

    @Mock
    private GenericPageable genericPageable;

    @Mock
    private GenericValidations genericValidations;

    @Test
    @DisplayName("Deve retornar erro quando não for localizado por id")
    public void shouldReturnErrorWhenNotFoundById(){
        final Long id = 1l;
        final Optional<OrdersEntity> optionalOrdersEntity = Optional.empty();
        when(ordersRepository.findById(id)).thenReturn(optionalOrdersEntity);
        assertThrows(CustomException.class, () -> ordersService.getById(id));
    }

    @Test
    @DisplayName("Não deve retornar erro quando o registro for encontrado")
    public void shouldNotReturnErrorWhatTheRegistryWasFound() {
        final Long id = 1l;
        final OrdersEntity ordersEntity = OrdersEntityBuilder.getInstance().getOrdersEntity();
        final Optional<OrdersEntity> optionalOrdersEntity = Optional.of(ordersEntity);
        when(ordersRepository.findById(id)).thenReturn(optionalOrdersEntity);
        compare(ordersEntity, assertDoesNotThrow(() -> ordersService.getById(id)));
    }

    private void compare(final OrdersEntity ordersEntity, final OrdersResponse ordersResponse) {
        assertNotNull(ordersEntity);
        assertNotNull(ordersResponse);
        assertEquals(ordersEntity.getId(), ordersResponse.getId());
        assertEquals(ordersEntity.getCompanyEntity().getId(), ordersResponse.getCompany().getId());
        assertEquals(ordersEntity.getCalculationFreightEntity().getId(), ordersResponse.getCalculationFreight().getId());
        assertEquals(ordersEntity.getOrderNumber(), ordersResponse.getNumber());
        assertEquals(ordersEntity.getDateCreate(), ordersResponse.getDateCreate());
    }

    @Test
    @DisplayName("Deve retornar erro quando não localizado por número do pedido")
    public void shouldReturnErrorWhenNotLocatedByOrderNumber() {
        final String orderNumber = "123";
        final Long companyId = 1L;
        final CompanyEntity companyEntity = CompanyEntityBuilder.getBasicCompanyEntity().getCompanyEntity();
        when(companyService.getCompanyEntityById(companyId)).thenReturn(companyEntity);
        final OrdersEntity ordersEntity = null;
        when(ordersRepository.findOrdersByNumberAndCompany(orderNumber, companyService.getCompanyEntityById(companyId).getId())).thenReturn(ordersEntity);
        assertThrows(CustomException.class, () -> ordersService.getByOrder(orderNumber, companyId));
    }

    @Test
    @DisplayName("Não deve retornar erro quando localizado por número do pedido")
    public void shouldNotReturnErrorWhenLocatedByOrderNumber() {
        final String orderNumber = "123";
        final Long companyId = 1L;
        final CompanyEntity companyEntity = CompanyEntityBuilder.getBasicCompanyEntity().getCompanyEntity();
        when(companyService.getCompanyEntityById(companyId)).thenReturn(companyEntity);
        final OrdersEntity ordersEntity = OrdersEntityBuilder.getInstance().getOrdersEntity();
        when(ordersRepository.findOrdersByNumberAndCompany(orderNumber, companyService.getCompanyEntityById(companyId).getId())).thenReturn(ordersEntity);
        compare(ordersEntity, assertDoesNotThrow(() -> ordersService.getByOrder(orderNumber, companyId)));
    }
}