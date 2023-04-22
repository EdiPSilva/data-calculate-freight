package br.com.java.datacalculatefreight.application.calculationTypeRangeFreight;

import br.com.java.datacalculatefreight.application.calculationTypeRangeFreight.builders.CalculationTypeRangeFreightEntityBuilder;
import br.com.java.datacalculatefreight.application.calculationTypeRangeFreight.builders.CalculationTypeRangeFreightRequestBuilder;
import br.com.java.datacalculatefreight.application.calculationTypeRangeFreight.persistence.CalculationTypeRangeFreightEntity;
import br.com.java.datacalculatefreight.application.calculationTypeRangeFreight.persistence.CalculationTypeRangeFreightRepository;
import br.com.java.datacalculatefreight.application.calculationTypeRangeFreight.resources.CalculationTypeRangeFreightRequest;
import br.com.java.datacalculatefreight.application.calculationTypeRangeFreight.resources.CalculationTypeRangeFreightResponse;
import br.com.java.datacalculatefreight.application.freightRoute.FreightRouteService;
import br.com.java.datacalculatefreight.application.freightRoute.builders.FreightRouteEntityBuilder;
import br.com.java.datacalculatefreight.application.freightRoute.persistence.FreightRouteEntity;
import br.com.java.datacalculatefreight.application.rangeFreight.RangeFreightService;
import br.com.java.datacalculatefreight.application.rangeFreight.builders.RangeFreightEntityBuilder;
import br.com.java.datacalculatefreight.application.rangeFreight.persistence.RangeFreightEntity;
import br.com.java.datacalculatefreight.application.typeDelivery.TypeDeliveryService;
import br.com.java.datacalculatefreight.application.typeDelivery.builders.TypeDeliveryEntityBuilder;
import br.com.java.datacalculatefreight.application.typeDelivery.persistence.TypeDeliveryEntity;
import br.com.java.datacalculatefreight.configuration.MessageConfiguration;
import br.com.java.datacalculatefreight.exceptions.CustomException;
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
public class CalculationTypeRangeFreightServiceTest {
    
    @InjectMocks
    private CalculationTypeRangeFreightService calculationTypeRangeFreightService;

    @Mock
    private MessageConfiguration messageConfiguration;

    @Mock
    private GenericValidations genericValidations;

    @Mock
    private CalculationTypeRangeFreightRepository calculationTypeRangeFreightRepository;

    @Mock
    private RangeFreightService rangeFreightService;

    @Mock
    private FreightRouteService freightRouteService;

    @Mock
    private TypeDeliveryService typeDeliveryService;

    @Test
    @DisplayName("Deve retornar erro quando não for localizado por id")
    public void shouldReturnErrorWhenNotFoundById(){
        final Long id = 1l;
        final Optional<CalculationTypeRangeFreightEntity> optionalCalculationTypeRangeFreightEntity = Optional.empty();
        when(calculationTypeRangeFreightRepository.findById(id)).thenReturn(optionalCalculationTypeRangeFreightEntity);
        assertThrows(CustomException.class, () -> calculationTypeRangeFreightService.getById(id));
    }

    @Test
    @DisplayName("Não deve retornar erro quando o registro for encontrado")
    public void shouldNotReturnErrorWhatTheRegistryWasFound() {
        final Long id = 1l;
        final CalculationTypeRangeFreightEntity calculationTypeRangeFreightEntity = CalculationTypeRangeFreightEntityBuilder.getInstance(id).getCalculationTypeRangeFreightEntity();
        final Optional<CalculationTypeRangeFreightEntity> optionalCalculationTypeRangeFreightEntity = Optional.of(calculationTypeRangeFreightEntity);
        when(calculationTypeRangeFreightRepository.findById(id)).thenReturn(optionalCalculationTypeRangeFreightEntity);
        compare(calculationTypeRangeFreightEntity, assertDoesNotThrow(() -> calculationTypeRangeFreightService.getById(id)));
    }

    private void compare(final CalculationTypeRangeFreightEntity calculationTypeRangeFreightEntity, final CalculationTypeRangeFreightResponse calculationTypeRangeFreightResponse) {
        assertNotNull(calculationTypeRangeFreightEntity);
        assertNotNull(calculationTypeRangeFreightResponse);
        assertEquals(calculationTypeRangeFreightEntity.getId(), calculationTypeRangeFreightResponse.getId());
        assertEquals(calculationTypeRangeFreightEntity.getCalculationType().getValue(), calculationTypeRangeFreightResponse.getCalculationType());
        assertEquals(calculationTypeRangeFreightEntity.getDateCreate(), calculationTypeRangeFreightResponse.getDateCreate());
        assertEquals(calculationTypeRangeFreightEntity.getRangeFreightEntity().getId(), calculationTypeRangeFreightResponse.getRangeFreight().getId());
        assertEquals(calculationTypeRangeFreightEntity.getFreightRouteEntity().getId(), calculationTypeRangeFreightResponse.getFreightRoute().getId());
        assertEquals(calculationTypeRangeFreightEntity.getTypeDeliveryEntity().getId(), calculationTypeRangeFreightResponse.getTypeDelivery().getId());
    }

    @Test
    @DisplayName("deve retornar erro quando as chaves já existirem")
    public void shouldReturnErrorWhenTheKeysAlreadyExists() {
        final CalculationTypeRangeFreightRequest calculationTypeRangeFreightRequest = CalculationTypeRangeFreightRequestBuilder.getInstance().getCalculationTypeRangeFreightRequest();
        final CalculationTypeRangeFreightEntity calculationTypeRangeFreightEntity = CalculationTypeRangeFreightEntityBuilder.getInstance(calculationTypeRangeFreightRequest).getCalculationTypeRangeFreightEntity();

        final RangeFreightEntity rangeFreightEntity = RangeFreightEntityBuilder.getBasicRangeFreightEntity(calculationTypeRangeFreightRequest.getRangeFreightId()).getRangeFreightEntity();
        final FreightRouteEntity freightRouteEntity = FreightRouteEntityBuilder.getBasicFreightRouteEntity(calculationTypeRangeFreightRequest.getFreightRouteId()).getFreightRouteEntity();
        final TypeDeliveryEntity typeDeliveryEntity = TypeDeliveryEntityBuilder.getBasicTypeDeliveryEntity(calculationTypeRangeFreightRequest.getTypeDelivery()).getTypeDeliveryEntity();

        when(rangeFreightService.getRangeFreightById(calculationTypeRangeFreightRequest.getRangeFreightId())).thenReturn(rangeFreightEntity);
        when(freightRouteService.getFreightRouteById(calculationTypeRangeFreightRequest.getFreightRouteId())).thenReturn(freightRouteEntity);
        when(typeDeliveryService.getTypeDeliveryById(calculationTypeRangeFreightRequest.getTypeDelivery())).thenReturn(typeDeliveryEntity);
        when(genericValidations.getCalculationTypeEnum(calculationTypeRangeFreightRequest.getCalculationType())).thenReturn(calculationTypeRangeFreightEntity.getCalculationType());

        final Long existsId = 1L;
        when(calculationTypeRangeFreightRepository.findAlreadyExistsCalculationTypeRangeFreight(calculationTypeRangeFreightEntity.getCalculationType(), rangeFreightEntity.getId(), freightRouteEntity.getId(), typeDeliveryEntity.getId())).thenReturn(existsId);

        assertThrows(CustomException.class, () -> calculationTypeRangeFreightService.create(calculationTypeRangeFreightRequest));
    }

    @Test
    @DisplayName("Não deve retornar erro quando as chaves não existirem")
    public void shouldNotReturnErrorWhenTheKeysNotExists() {
        final CalculationTypeRangeFreightRequest calculationTypeRangeFreightRequest = CalculationTypeRangeFreightRequestBuilder.getInstance().getCalculationTypeRangeFreightRequest();
        final CalculationTypeRangeFreightEntity calculationTypeRangeFreightEntity = CalculationTypeRangeFreightEntityBuilder.getInstance(calculationTypeRangeFreightRequest).getCalculationTypeRangeFreightEntity();

        final RangeFreightEntity rangeFreightEntity = RangeFreightEntityBuilder.getBasicRangeFreightEntity(calculationTypeRangeFreightRequest.getRangeFreightId()).getRangeFreightEntity();
        final FreightRouteEntity freightRouteEntity = FreightRouteEntityBuilder.getBasicFreightRouteEntity(calculationTypeRangeFreightRequest.getFreightRouteId()).getFreightRouteEntity();
        final TypeDeliveryEntity typeDeliveryEntity = TypeDeliveryEntityBuilder.getBasicTypeDeliveryEntity(calculationTypeRangeFreightRequest.getTypeDelivery()).getTypeDeliveryEntity();

        when(rangeFreightService.getRangeFreightById(calculationTypeRangeFreightRequest.getRangeFreightId())).thenReturn(rangeFreightEntity);
        when(freightRouteService.getFreightRouteById(calculationTypeRangeFreightRequest.getFreightRouteId())).thenReturn(freightRouteEntity);
        when(typeDeliveryService.getTypeDeliveryById(calculationTypeRangeFreightRequest.getTypeDelivery())).thenReturn(typeDeliveryEntity);
        when(genericValidations.getCalculationTypeEnum(calculationTypeRangeFreightRequest.getCalculationType())).thenReturn(calculationTypeRangeFreightEntity.getCalculationType());

        final Long existsId = null;
        when(calculationTypeRangeFreightRepository.findAlreadyExistsCalculationTypeRangeFreight(calculationTypeRangeFreightEntity.getCalculationType(), rangeFreightEntity.getId(), freightRouteEntity.getId(), typeDeliveryEntity.getId())).thenReturn(existsId);

        when(calculationTypeRangeFreightRepository.save(Mockito.any())).thenReturn(calculationTypeRangeFreightEntity);
        compare(calculationTypeRangeFreightEntity, assertDoesNotThrow(() -> calculationTypeRangeFreightService.create(calculationTypeRangeFreightRequest)));
    }

    @Test
    @DisplayName("deve retornar erro quando as chaves já existirem na atualização")
    public void shouldReturnErrorWhenTheKeysAlreadyExistsInTheUpdate() {
        final CalculationTypeRangeFreightRequest calculationTypeRangeFreightRequest = CalculationTypeRangeFreightRequestBuilder.getInstance().getCalculationTypeRangeFreightRequest();
        final CalculationTypeRangeFreightEntity calculationTypeRangeFreightEntity = CalculationTypeRangeFreightEntityBuilder.getInstance(calculationTypeRangeFreightRequest).getCalculationTypeRangeFreightEntity();

        final RangeFreightEntity rangeFreightEntity = RangeFreightEntityBuilder.getBasicRangeFreightEntity(calculationTypeRangeFreightRequest.getRangeFreightId()).getRangeFreightEntity();
        final FreightRouteEntity freightRouteEntity = FreightRouteEntityBuilder.getBasicFreightRouteEntity(calculationTypeRangeFreightRequest.getFreightRouteId()).getFreightRouteEntity();
        final TypeDeliveryEntity typeDeliveryEntity = TypeDeliveryEntityBuilder.getBasicTypeDeliveryEntity(calculationTypeRangeFreightRequest.getTypeDelivery()).getTypeDeliveryEntity();

        when(rangeFreightService.getRangeFreightById(calculationTypeRangeFreightRequest.getRangeFreightId())).thenReturn(rangeFreightEntity);
        when(freightRouteService.getFreightRouteById(calculationTypeRangeFreightRequest.getFreightRouteId())).thenReturn(freightRouteEntity);
        when(typeDeliveryService.getTypeDeliveryById(calculationTypeRangeFreightRequest.getTypeDelivery())).thenReturn(typeDeliveryEntity);
        when(genericValidations.getCalculationTypeEnum(calculationTypeRangeFreightRequest.getCalculationType())).thenReturn(calculationTypeRangeFreightEntity.getCalculationType());

        final Long existsId = 1L;
        when(calculationTypeRangeFreightRepository.findAlreadyExistsCalculationTypeRangeFreight(calculationTypeRangeFreightEntity.getCalculationType(), rangeFreightEntity.getId(), freightRouteEntity.getId(), typeDeliveryEntity.getId())).thenReturn(existsId);

        assertThrows(CustomException.class, () -> calculationTypeRangeFreightService.update(existsId, calculationTypeRangeFreightRequest));
    }

    @Test
    @DisplayName("Não deve retornar erro quando as chaves não existirem na atualização")
    public void shouldNotReturnErrorWhenTheKeysNotExistsInTheUpdate() {
        final Long id = 1L;
        final CalculationTypeRangeFreightRequest calculationTypeRangeFreightRequest = CalculationTypeRangeFreightRequestBuilder.getInstance().getCalculationTypeRangeFreightRequest();
        final CalculationTypeRangeFreightEntity calculationTypeRangeFreightEntity = CalculationTypeRangeFreightEntityBuilder.getInstance(calculationTypeRangeFreightRequest).getCalculationTypeRangeFreightEntity();
        final Optional<CalculationTypeRangeFreightEntity> optionalCalculationTypeRangeFreightEntity = Optional.of(calculationTypeRangeFreightEntity);
        when(calculationTypeRangeFreightRepository.findById(id)).thenReturn(optionalCalculationTypeRangeFreightEntity);

        final RangeFreightEntity rangeFreightEntity = RangeFreightEntityBuilder.getBasicRangeFreightEntity(calculationTypeRangeFreightRequest.getRangeFreightId()).getRangeFreightEntity();
        final FreightRouteEntity freightRouteEntity = FreightRouteEntityBuilder.getBasicFreightRouteEntity(calculationTypeRangeFreightRequest.getFreightRouteId()).getFreightRouteEntity();
        final TypeDeliveryEntity typeDeliveryEntity = TypeDeliveryEntityBuilder.getBasicTypeDeliveryEntity(calculationTypeRangeFreightRequest.getTypeDelivery()).getTypeDeliveryEntity();

        when(rangeFreightService.getRangeFreightById(calculationTypeRangeFreightRequest.getRangeFreightId())).thenReturn(rangeFreightEntity);
        when(freightRouteService.getFreightRouteById(calculationTypeRangeFreightRequest.getFreightRouteId())).thenReturn(freightRouteEntity);
        when(typeDeliveryService.getTypeDeliveryById(calculationTypeRangeFreightRequest.getTypeDelivery())).thenReturn(typeDeliveryEntity);
        when(genericValidations.getCalculationTypeEnum(calculationTypeRangeFreightRequest.getCalculationType())).thenReturn(calculationTypeRangeFreightEntity.getCalculationType());

        final Long existsId = null;
        when(calculationTypeRangeFreightRepository.findAlreadyExistsCalculationTypeRangeFreight(calculationTypeRangeFreightEntity.getCalculationType(), rangeFreightEntity.getId(), freightRouteEntity.getId(), typeDeliveryEntity.getId())).thenReturn(existsId);

        when(calculationTypeRangeFreightRepository.save(Mockito.any())).thenReturn(calculationTypeRangeFreightEntity);
        compare(calculationTypeRangeFreightEntity, assertDoesNotThrow(() -> calculationTypeRangeFreightService.update(id, calculationTypeRangeFreightRequest)));
    }

    @Test
    @DisplayName("Deve retornar erro quando não encontrado registro por id durante a exclusão")
    public void shouldReturnErrorWhenNotFoundRegisterByIdInRegistryDelete() {
        final Long id = 1l;
        final Optional<CalculationTypeRangeFreightEntity> optionalCalculationTypeRangeFreightEntity = Optional.empty();
        when(calculationTypeRangeFreightRepository.findById(id)).thenReturn(optionalCalculationTypeRangeFreightEntity);
        assertThrows(CustomException.class, () -> calculationTypeRangeFreightService.delete(id));
    }

    @Test
    @DisplayName("Não deve retornar erro quando encontrado registro por id durante a exclusão")
    public void shouldNotReturnErrorWhenFoundRegisterByIdInRegistryDelete() {
        final Long id = 1l;
        final CalculationTypeRangeFreightEntity calculationTypeRangeFreightEntity = CalculationTypeRangeFreightEntityBuilder.getInstance(id).getCalculationTypeRangeFreightEntity();
        final Optional<CalculationTypeRangeFreightEntity> optionalCalculationTypeRangeFreightEntity = Optional.of(calculationTypeRangeFreightEntity);
        when(calculationTypeRangeFreightRepository.findById(id)).thenReturn(optionalCalculationTypeRangeFreightEntity);

        final DefaultResponse defaultResponse = assertDoesNotThrow(() -> calculationTypeRangeFreightService.delete(id));
        assertNotNull(defaultResponse);
        assertNotNull(defaultResponse.getObject());
        assertEquals(StatusMessageEnum.SUCCESS.getValue(), defaultResponse.getStatus());

        final CalculationTypeRangeFreightResponse calculationTypeRangeFreightResponse = (CalculationTypeRangeFreightResponse) defaultResponse.getObject();

        compare(calculationTypeRangeFreightEntity, calculationTypeRangeFreightResponse);
    }
}
