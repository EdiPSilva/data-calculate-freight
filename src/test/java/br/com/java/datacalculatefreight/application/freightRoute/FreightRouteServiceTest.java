package br.com.java.datacalculatefreight.application.freightRoute;

import br.com.java.datacalculatefreight.application.freightRoute.builders.FreightRouteEntityBuilder;
import br.com.java.datacalculatefreight.application.freightRoute.builders.FreightRouteRequestBuilder;
import br.com.java.datacalculatefreight.application.freightRoute.persistence.FreightRouteEntity;
import br.com.java.datacalculatefreight.application.freightRoute.persistence.FreightRouteRepository;
import br.com.java.datacalculatefreight.application.freightRoute.resources.FreightRouteRequest;
import br.com.java.datacalculatefreight.application.freightRoute.resources.FreightRouteResponse;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FreightRouteServiceTest {

    @InjectMocks
    FreightRouteService freightRouteService;

    @Mock
    MessageConfiguration messageConfiguration;

    @Mock
    GenericValidations genericValidations;

    @Mock
    GenericPageable genericPageable;

    @Mock
    FreightRouteRepository freightRouteRepository;

    @Test
    @DisplayName("Deve retornar erro quando não for localizado a rota de frete por id")
    public void shouldReturnErrorWhenNotFoundFreightRouteById(){
        final Long id = 1l;
        final Optional<FreightRouteEntity> optionalFreightRouteEntity = Optional.empty();
        when(freightRouteRepository.findById(id)).thenReturn(optionalFreightRouteEntity);
        assertThrows(CustomException.class, () -> freightRouteService.getById(id));
    }

    @Test
    @DisplayName("Não deve retornar erro quando o registro for encontrado")
    public void shouldNotReturnErrorWhatTheRegistryWasFound() {
        final Long id = 1l;
        final FreightRouteEntity freightRouteEntity = FreightRouteEntityBuilder.getInstance(id).getFreightRouteEntity();
        final Optional<FreightRouteEntity> optionalFreightRouteEntity = Optional.of(freightRouteEntity);
        when(freightRouteRepository.findById(id)).thenReturn(optionalFreightRouteEntity);
        compare(freightRouteEntity, assertDoesNotThrow(() -> freightRouteService.getById(id)));
    }

    private void compare(final FreightRouteEntity freightRouteEntity, final FreightRouteResponse freightRouteResponse) {
        assertNotNull(freightRouteEntity);
        assertNotNull(freightRouteResponse);
        assertEquals(freightRouteEntity.getId(), freightRouteResponse.getId());
        assertEquals(freightRouteEntity.getStartPostalCode(), freightRouteResponse.getStartPostalCode());
        assertEquals(freightRouteEntity.getEndPostalCode(), freightRouteResponse.getEndPostalCode());
        assertEquals(freightRouteEntity.getDeliveryDays(), freightRouteResponse.getDeliveryDays());
        assertEquals(freightRouteEntity.getDevolutionDays(), freightRouteResponse.getDevolutionDays());
        assertEquals(freightRouteEntity.getActive(), freightRouteResponse.getActive());
        assertEquals(freightRouteEntity.getDateCreate(), freightRouteResponse.getDateCreate());

    }

    @Test
    @DisplayName("Deve retornar erro quanto não localizado nenhuma rota de frete por CEP")
    public void shouldReturnErroWhenNotLocateNoRouteByPostalCode() {
        final String postalCode = "14000000";
        final FreightRouteEntity freightRouteEntity = null;
        when(freightRouteRepository.findByStartPostalCode(postalCode)).thenReturn(freightRouteEntity);
        when(freightRouteRepository.findByEndPostalCode(postalCode)).thenReturn(freightRouteEntity);
        assertThrows(CustomException.class, () -> freightRouteService.getByPostalCode(postalCode, "start"), "Registro não encontrado");
        assertThrows(CustomException.class, () -> freightRouteService.getByPostalCode(postalCode, "end"), "Registro não encontrado");
    }

    @Test
    @DisplayName("Não deve retornar erro quanto não localizado nenhuma rota de frete por CEP")
    public void shouldNotReturnErroWhenLocateRouteByPostalCode() {
        final String startPostalCode = "14000000";
        final String endPostalCode = "14000020";
        final FreightRouteEntity freightRouteEntity = FreightRouteEntityBuilder.getInstance().getFreightRouteEntity();
        when(freightRouteRepository.findByStartPostalCode(startPostalCode)).thenReturn(freightRouteEntity);
        when(freightRouteRepository.findByEndPostalCode(endPostalCode)).thenReturn(freightRouteEntity);
        compare(freightRouteEntity, freightRouteService.getByPostalCode(startPostalCode, "start"));
        compare(freightRouteEntity, freightRouteService.getByPostalCode(endPostalCode, "end"));
    }

    @Test
    @DisplayName("Deve retornar erro quando CEP já existir")
    public void shouldReturnErrorWhenPostalCodeAlreadyExists() {
        final FreightRouteRequest freightRouteRequest = FreightRouteRequestBuilder.getInstance().getFreightRouteRequest();
        final Long existingId = 1l;
        when(freightRouteRepository.findFreightRouteEntityByStartPostalCode(Mockito.anyString())).thenReturn(existingId);
        when(freightRouteRepository.findFreightRouteEntityByEndPostalCode(Mockito.anyString())).thenReturn(existingId);
        assertThrows(CustomException.class, () -> freightRouteService.create(freightRouteRequest), "O " + freightRouteRequest.getStartPostalCode() + " já está cadastrado como CEP inicial");
        assertThrows(CustomException.class, () -> freightRouteService.create(freightRouteRequest), "O " + freightRouteRequest.getEndPostalCode() + " já está cadastrado como CEP final");
    }

    @Test
    @DisplayName("Deve retornar erro quando CEP inicial e final for igual")
    public void shouldReturnErrorWhenStartAndEndPostalCodeAreEquals() {
        final boolean equals = true;
        final FreightRouteRequest freightRouteRequest = FreightRouteRequestBuilder.getInstance(equals).getFreightRouteRequest();
        assertThrows(CustomException.class, () -> freightRouteService.create(freightRouteRequest), "Os CEPs inicial e final não podem ser iguais");
    }

    @Test
    @DisplayName("Não deve retornar erro quando os CEPs não existirem")
    public void shouldNotReturnErrorWhenThePostalCodesNotExists() {
        final FreightRouteRequest freightRouteRequest = FreightRouteRequestBuilder.getInstance().getFreightRouteRequest();
        final FreightRouteEntity freightRouteEntity = FreightRouteEntityBuilder.getInstance(freightRouteRequest).getFreightRouteEntity();
        freightRouteEntity.setId(1L);
        final Long existingId = null;
        when(freightRouteRepository.findFreightRouteEntityByStartPostalCode(Mockito.anyString())).thenReturn(existingId);
        when(freightRouteRepository.findFreightRouteEntityByEndPostalCode(Mockito.anyString())).thenReturn(existingId);
        when(freightRouteRepository.save(Mockito.any())).thenReturn(freightRouteEntity);
        compare(freightRouteRequest, assertDoesNotThrow(() -> freightRouteService.create(freightRouteRequest)));
    }

    private void compare(final FreightRouteRequest freightRouteRequest, final FreightRouteResponse freightRouteResponse) {
        assertNotNull(freightRouteRequest);
        assertNotNull(freightRouteResponse);
        assertNotNull(freightRouteResponse.getId());
        assertEquals(freightRouteRequest.getStartPostalCode(), freightRouteResponse.getStartPostalCode());
        assertEquals(freightRouteRequest.getEndPostalCode(), freightRouteResponse.getEndPostalCode());
        assertEquals(freightRouteRequest.getDeliveryDays(), freightRouteResponse.getDeliveryDays());
        assertEquals(freightRouteRequest.getDevolutionDays(), freightRouteResponse.getDevolutionDays());
        assertEquals(freightRouteRequest.getActive(), freightRouteResponse.getActive());
    }

    @Test
    @DisplayName("Deve retornar erro quando o CEP estiver vinculado a outra rota de frete")
    public void shouldReturnErrorWhenThePostalCodeIsLinckedToAnotherFreightRoute() {
        final Long id = 1L;
        final Long existingId = 2L;
        final FreightRouteRequest freightRouteRequest = FreightRouteRequestBuilder.getInstance().getFreightRouteRequest();

        when(freightRouteRepository.findFreightRouteEntityByStartPostalCode(Mockito.anyString())).thenReturn(existingId);
        when(freightRouteRepository.findFreightRouteEntityByEndPostalCode(Mockito.anyString())).thenReturn(null);
        assertThrows(CustomException.class, () -> freightRouteService.update(id, freightRouteRequest));

        when(freightRouteRepository.findFreightRouteEntityByStartPostalCode(Mockito.anyString())).thenReturn(null);
        when(freightRouteRepository.findFreightRouteEntityByEndPostalCode(Mockito.anyString())).thenReturn(existingId);
        assertThrows(CustomException.class, () -> freightRouteService.update(id, freightRouteRequest));
    }

    @Test
    @DisplayName("Não deve retornar erro quanto os CEPs for válido")
    public void shouldNotReturnErrorWhenThePostalCodesWasValid() {
        final Long id = 1L;
        final Long existingId = 1L;
        final FreightRouteRequest freightRouteRequest = FreightRouteRequestBuilder.getInstance().getFreightRouteRequest();
        final FreightRouteEntity freightRouteEntity = FreightRouteEntityBuilder.getInstance(freightRouteRequest).getFreightRouteEntity();
        freightRouteEntity.setId(id);
        final Optional<FreightRouteEntity> optionalFreightRouteEntity = Optional.of(freightRouteEntity);

        when(freightRouteRepository.findById(id)).thenReturn(optionalFreightRouteEntity);
        when(freightRouteRepository.save(Mockito.any())).thenReturn(freightRouteEntity);

        when(freightRouteRepository.findFreightRouteEntityByStartPostalCode(Mockito.anyString())).thenReturn(existingId);
        when(freightRouteRepository.findFreightRouteEntityByEndPostalCode(Mockito.anyString())).thenReturn(null);
        compare(freightRouteRequest, assertDoesNotThrow(() -> freightRouteService.update(id, freightRouteRequest)));

        when(freightRouteRepository.findFreightRouteEntityByStartPostalCode(Mockito.anyString())).thenReturn(null);
        when(freightRouteRepository.findFreightRouteEntityByEndPostalCode(Mockito.anyString())).thenReturn(existingId);
        compare(freightRouteRequest, assertDoesNotThrow(() -> freightRouteService.update(id, freightRouteRequest)));

        when(freightRouteRepository.findFreightRouteEntityByStartPostalCode(Mockito.anyString())).thenReturn(existingId);
        when(freightRouteRepository.findFreightRouteEntityByEndPostalCode(Mockito.anyString())).thenReturn(existingId);
        compare(freightRouteRequest, assertDoesNotThrow(() -> freightRouteService.update(id, freightRouteRequest)));

        when(freightRouteRepository.findFreightRouteEntityByStartPostalCode(Mockito.anyString())).thenReturn(null);
        when(freightRouteRepository.findFreightRouteEntityByEndPostalCode(Mockito.anyString())).thenReturn(null);
        compare(freightRouteRequest, assertDoesNotThrow(() -> freightRouteService.update(id, freightRouteRequest)));
    }

    @Test
    @DisplayName("Deve retornar erro quando não encontrado rota de frete por id durante a exclusão")
    public void shouldReturnErrorWhenNotFoundFreightRouteByIdInRegistryDelete() {
        final Long id = 1L;
        final Optional<FreightRouteEntity> optionalFreightRouteEntity = Optional.empty();
        when(freightRouteRepository.findById(id)).thenReturn(optionalFreightRouteEntity);
        assertThrows(CustomException.class, () -> freightRouteService.delete(id));
    }

    @Test
    @DisplayName("Não deve retornar erro quando encontrado rota de frete por id durante a exclusão")
    public void shouldNotReturnErrorWhenFoundFreightRouteByIdInRegistryDelete() {
        final Long id = 1L;
        final FreightRouteEntity freightRouteEntity = FreightRouteEntityBuilder.getInstance(id).getFreightRouteEntity();
        final Optional<FreightRouteEntity> optionalFreightRouteEntity = Optional.of(freightRouteEntity);
        when(freightRouteRepository.findById(id)).thenReturn(optionalFreightRouteEntity);

        final DefaultResponse defaultResponse = assertDoesNotThrow(() -> freightRouteService.delete(id));
        assertNotNull(defaultResponse);
        assertNotNull(defaultResponse.getObject());
        assertEquals(StatusMessageEnum.SUCCESS.getValue(), defaultResponse.getStatus());

        final FreightRouteResponse freightRouteResponse = (FreightRouteResponse) defaultResponse.getObject();
        compare(freightRouteEntity, freightRouteResponse);
    }
}