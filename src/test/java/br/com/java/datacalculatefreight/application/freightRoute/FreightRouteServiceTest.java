package br.com.java.datacalculatefreight.application.freightRoute;

import br.com.java.datacalculatefreight.application.freightRoute.builders.FreightRouteEntityBuilder;
import br.com.java.datacalculatefreight.application.freightRoute.builders.FreightRouteRequestBuilder;
import br.com.java.datacalculatefreight.application.freightRoute.persistence.FreightRouteEntity;
import br.com.java.datacalculatefreight.application.freightRoute.persistence.FreightRouteRepository;
import br.com.java.datacalculatefreight.application.freightRoute.resources.FreightRouteRequest;
import br.com.java.datacalculatefreight.application.freightRoute.resources.FreightRouteResponse;
import br.com.java.datacalculatefreight.configuration.MessageCodeEnum;
import br.com.java.datacalculatefreight.configuration.MessageConfiguration;
import br.com.java.datacalculatefreight.exceptions.CustomException;
import br.com.java.datacalculatefreight.pageable.GenericPageable;
import br.com.java.datacalculatefreight.utils.GenericValidations;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

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
        final FreightRouteEntity freightRouteEntity = FreightRouteEntityBuilder.getBasicFreightRouteEntity(id).getFreightRouteEntity();
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
        final FreightRouteEntity freightRouteEntity = FreightRouteEntityBuilder.getBasicFreightRouteEntity().getFreightRouteEntity();
        when(freightRouteRepository.findByStartPostalCode(startPostalCode)).thenReturn(freightRouteEntity);
        when(freightRouteRepository.findByEndPostalCode(endPostalCode)).thenReturn(freightRouteEntity);
        compare(freightRouteEntity, freightRouteService.getByPostalCode(startPostalCode, "start"));
        compare(freightRouteEntity, freightRouteService.getByPostalCode(endPostalCode, "end"));
    }

    @Test
    @DisplayName("Deve retornar erro quando CEP já existir")
    public void shouldReturnErrorWhenPostalCodeAlreadyExists() {
        final FreightRouteRequest freightRouteRequest = FreightRouteRequestBuilder.getBasicFreightRouteRequest().getFreightRouteRequest();
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
        final FreightRouteRequest freightRouteRequest = FreightRouteRequestBuilder.getBasicFreightRouteRequest(equals).getFreightRouteRequest();
        assertThrows(CustomException.class, () -> freightRouteService.create(freightRouteRequest), "Os CEPs inicial e final não podem ser iguais");
    }
}