package br.com.java.datacalculatefreight.application.calculationFreight;

import br.com.java.datacalculatefreight.application.calculationFreight.builders.CalculationFreightEntityBuilder;
import br.com.java.datacalculatefreight.application.calculationFreight.persistence.CalculationFreightEntity;
import br.com.java.datacalculatefreight.application.calculationFreight.persistence.CalculationFreightRepository;
import br.com.java.datacalculatefreight.application.calculationFreight.resources.CalculationFreightResponse;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CalculationFreightServiceTest {

    @InjectMocks
    CalculationFreightService calculationFreightService;

    @Mock
    MessageConfiguration messageConfiguration;

    @Mock
    GenericValidations genericValidations;

    @Mock
    GenericPageable genericPageable;

    @Mock
    CalculationFreightRepository calculationFreightRepository;

    @Test
    @DisplayName("Deve retornar erro quando não for localizado a empresa por id")
    public void shouldReturnErrorWhenNotFoundCompanyById(){
        final Long id = 1l;
        final Optional<CalculationFreightEntity> optionalCalculationFreightEntity = Optional.empty();
        when(calculationFreightRepository.findById(id)).thenReturn(optionalCalculationFreightEntity);
        assertThrows(CustomException.class, () -> calculationFreightService.getById(id));
    }

    @Test
    @DisplayName("Não deve retornar erro quando o registro for encontrado")
    public void shouldNotReturnErrorWhatTheRegistryWasFound() {
        final Long id = 1l;
        final CalculationFreightEntity calculationFreightEntity = CalculationFreightEntityBuilder.getBasicCalculationFreightEntity(id).getCalculationFreightEntity();
        final Optional<CalculationFreightEntity> optionalCalculationFreightEntity = Optional.of(calculationFreightEntity);
        when(calculationFreightRepository.findById(id)).thenReturn(optionalCalculationFreightEntity);
        compare(calculationFreightEntity, assertDoesNotThrow(() -> calculationFreightService.getById(id)));
    }

    private void compare(final CalculationFreightEntity calculationFreightEntity, final CalculationFreightResponse calculationFreightResponse) {
        assertNotNull(calculationFreightEntity);
        assertNotNull(calculationFreightResponse);
        assertEquals(calculationFreightEntity.getId(), calculationFreightResponse.getId());
        assertEquals(calculationFreightEntity.getDestinyPostalCode(), calculationFreightResponse.getDestinyPostalCode());
        assertEquals(calculationFreightEntity.getWidth(), calculationFreightResponse.getWidth());
        assertEquals(calculationFreightEntity.getHeight(), calculationFreightResponse.getHeight());
        assertEquals(calculationFreightEntity.getCubage(), calculationFreightResponse.getCubage());
        assertEquals(calculationFreightEntity.getWeight(), calculationFreightResponse.getWeight());
        assertEquals(calculationFreightEntity.getFreightValue(), calculationFreightResponse.getFreightValue());
        assertEquals(calculationFreightEntity.getDateCreate(), calculationFreightResponse.getDateCreate());
    }
}
