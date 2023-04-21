package br.com.java.datacalculatefreight.application.rangeFreight;

import br.com.java.datacalculatefreight.application.rangeFreight.builders.RangeFreightEntityBuilder;
import br.com.java.datacalculatefreight.application.rangeFreight.builders.RangeFreightRequestBuilder;
import br.com.java.datacalculatefreight.application.rangeFreight.persistence.RangeFreightEntity;
import br.com.java.datacalculatefreight.application.rangeFreight.persistence.RangeFreightRepository;
import br.com.java.datacalculatefreight.application.rangeFreight.resources.RangeFreightRequest;
import br.com.java.datacalculatefreight.application.rangeFreight.resources.RangeFreightResponse;
import br.com.java.datacalculatefreight.application.shippingCompany.ShippingCompanyService;
import br.com.java.datacalculatefreight.application.shippingCompany.builders.ShippingCompanyEntityBuilder;
import br.com.java.datacalculatefreight.application.shippingCompany.persistence.ShippingCompanyEntity;
import br.com.java.datacalculatefreight.configuration.MessageConfiguration;
import br.com.java.datacalculatefreight.exceptions.CustomException;
import br.com.java.datacalculatefreight.pageable.GenericPageable;
import br.com.java.datacalculatefreight.utils.DefaultResponse;
import br.com.java.datacalculatefreight.utils.GenericValidations;
import br.com.java.datacalculatefreight.utils.StatusMessageEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RangeFreightServiceTest {

    @InjectMocks
    private RangeFreightService rangeFreightService;

    @Mock
    private MessageConfiguration messageConfiguration;

    @Mock
    private GenericValidations genericValidations;

    @Mock
    private GenericPageable genericPageable;

    @Mock
    private RangeFreightRepository rangeFreightRepository;

    @Mock
    private ShippingCompanyService shippingCompanyService;

    @Test
    @DisplayName("Deve retornar erro quando não for localizado o range de frete por id")
    public void shouldReturnErrorWhenNotFoundRangeFreightById(){
        final Long id = 1l;
        final Optional<RangeFreightEntity> optionalRangeFreightEntity = Optional.empty();
        when(rangeFreightRepository.findById(id)).thenReturn(optionalRangeFreightEntity);
        assertThrows(CustomException.class, () -> rangeFreightService.getById(id));
    }

    @Test
    @DisplayName("Não deve retornar erro quando o registro for encontrado")
    public void shouldNotReturnErroWhenTheRegistryWasFound() {
        final Long id = 1l;
        final RangeFreightEntity rangeFreightEntity = RangeFreightEntityBuilder.getBasicRangeFreightEntity(id).getRangeFreightEntity();
        final Optional<RangeFreightEntity> optionalRangeFreightEntity = Optional.of(rangeFreightEntity);
        when(rangeFreightRepository.findById(id)).thenReturn(optionalRangeFreightEntity);
        compare(rangeFreightEntity, assertDoesNotThrow(() -> rangeFreightService.getById(id)));
    }

    private void compare(final RangeFreightEntity rangeFreightEntity, final RangeFreightResponse rangeFreightResponse) {
        assertNotNull(rangeFreightEntity);
        assertNotNull(rangeFreightResponse);
        assertEquals(rangeFreightEntity.getId(), rangeFreightResponse.getId());
        assertNotNull(rangeFreightEntity.getShippingCompanyEntity());
        assertNotNull(rangeFreightResponse.getShippingCompany());
        assertEquals(rangeFreightEntity.getShippingCompanyEntity().getId(), rangeFreightResponse.getShippingCompany().getId());
        assertEquals(rangeFreightEntity.getStartValue(), rangeFreightResponse.getStartValue());
        assertEquals(rangeFreightEntity.getEndValue(), rangeFreightResponse.getEndValue());
        assertEquals(rangeFreightEntity.getFreightValue(), rangeFreightResponse.getFreightValue());
        assertEquals(rangeFreightEntity.getSurplusValue(), rangeFreightResponse.getSurplusValue());
        assertEquals(rangeFreightEntity.getActive(), rangeFreightResponse.getActive());
        assertEquals(rangeFreightEntity.getDateCreate(), rangeFreightResponse.getDateCreate());
    }

    @Test
    @DisplayName("Não deve retornar erro quando a transportadora for encontrada")
    public void shouldNotReturnErrorWhenShippingCompanyWasFound() {
        final RangeFreightRequest rangeFreightRequest = RangeFreightRequestBuilder.getBasicRangeFreightRequest().getRangeFreightRequest();
        final ShippingCompanyEntity shippingCompanyEntity = ShippingCompanyEntityBuilder.getBasicShippingCompanyEntity().getShippingCompanyEntity();
        final RangeFreightEntity rangeFreightEntity = RangeFreightEntityBuilder.getBasicRangeFreightEntityByRangeFreightRequest(rangeFreightRequest, shippingCompanyEntity).getRangeFreightEntity();
        when(shippingCompanyService.getShippingCompanyById(rangeFreightRequest.getShippingCompanyId())).thenReturn(shippingCompanyEntity);
        when(rangeFreightRepository.save(Mockito.any())).thenReturn(rangeFreightEntity);
        compare(rangeFreightRequest, assertDoesNotThrow(() -> rangeFreightService.create(rangeFreightRequest)));
    }

    private void compare(final RangeFreightRequest rangeFreightRequest, final RangeFreightResponse rangeFreightResponse) {
        assertNotNull(rangeFreightRequest);
        assertNotNull(rangeFreightResponse);
        assertNotNull(rangeFreightResponse.getShippingCompany());
        assertEquals(rangeFreightRequest.getShippingCompanyId(), rangeFreightResponse.getShippingCompany().getId());
        assertEquals(rangeFreightRequest.getStartValue(), rangeFreightResponse.getStartValue());
        assertEquals(rangeFreightRequest.getEndValue(), rangeFreightResponse.getEndValue());
        assertEquals(rangeFreightRequest.getFreightValue(), rangeFreightResponse.getFreightValue());
        assertEquals(rangeFreightRequest.getSurplusValue(), rangeFreightResponse.getSurplusValue());
        assertEquals(rangeFreightRequest.getActive(), rangeFreightResponse.getActive());
        assertNotNull(rangeFreightResponse.getDateCreate());
    }

    @Test
    @DisplayName("Não deve retornar erro quando a transportadora for encontrada durante a atualização")
    public void shouldNotReturnErrorWhenShippingCompanyWasFoundInRegistryUpdate() {
        final Long id = 1L;
        final RangeFreightRequest rangeFreightRequest = RangeFreightRequestBuilder.getBasicRangeFreightRequest().getRangeFreightRequest();
        final ShippingCompanyEntity shippingCompanyEntity = ShippingCompanyEntityBuilder.getBasicShippingCompanyEntity().getShippingCompanyEntity();
        final RangeFreightEntity rangeFreightEntity = RangeFreightEntityBuilder.getBasicRangeFreightEntityByRangeFreightRequest(rangeFreightRequest, shippingCompanyEntity).getRangeFreightEntity();
        when(shippingCompanyService.getShippingCompanyById(rangeFreightRequest.getShippingCompanyId())).thenReturn(shippingCompanyEntity);
        final Optional<RangeFreightEntity> optionalRangeFreightEntity = Optional.of(rangeFreightEntity);
        when(rangeFreightRepository.findById(id)).thenReturn(optionalRangeFreightEntity);
        when(rangeFreightRepository.save(Mockito.any())).thenReturn(rangeFreightEntity);
        compare(rangeFreightRequest, assertDoesNotThrow(() -> rangeFreightService.update(id, rangeFreightRequest)));
    }

    @Test
    @DisplayName("Deve retornar erro quando não encontrado transportadora por id durante a exclusão")
    public void shouldReturnErrorWhenNotFoundShippingCompanyByIdInRegistryDelete() {
        final Long id = 1l;
        final Optional<RangeFreightEntity> optionalRangeFreightEntity = Optional.empty();
        when(rangeFreightRepository.findById(id)).thenReturn(optionalRangeFreightEntity);
        assertThrows(CustomException.class, () -> rangeFreightService.delete(id));
    }

    @Test
    @DisplayName("Não deve retornar erro quando encontrado transportadora por id durante a exclusão")
    public void shouldNotReturnErrorWhenFoundShippingCompanyByIdInRegistryDelete() {
        final Long id = 1l;
        final RangeFreightEntity rangeFreightEntity = RangeFreightEntityBuilder.getBasicRangeFreightEntity(id).getRangeFreightEntity();
        final Optional<RangeFreightEntity> optionalRangeFreightEntity = Optional.of(rangeFreightEntity);
        when(rangeFreightRepository.findById(id)).thenReturn(optionalRangeFreightEntity);

        final DefaultResponse defaultResponse = assertDoesNotThrow(() -> rangeFreightService.delete(id));
        assertNotNull(defaultResponse);
        assertNotNull(defaultResponse.getObject());
        assertEquals(StatusMessageEnum.SUCCESS.getValue(), defaultResponse.getStatus());

        final RangeFreightResponse rangeFreightResponse = (RangeFreightResponse) defaultResponse.getObject();

        compare(rangeFreightEntity, rangeFreightResponse);
    }
}
