package br.com.java.datacalculatefreight.application.countryStates;

import br.com.java.datacalculatefreight.application.company.builders.CompanyEntityBuilder;
import br.com.java.datacalculatefreight.application.company.persistence.CompanyEntity;
import br.com.java.datacalculatefreight.application.countryStates.builders.CountryStatesEntityBuilder;
import br.com.java.datacalculatefreight.application.countryStates.persistence.CountryStatesEntity;
import br.com.java.datacalculatefreight.application.countryStates.persistence.CountryStatesRepository;
import br.com.java.datacalculatefreight.application.countryStates.resources.CountryStatesResponse;
import br.com.java.datacalculatefreight.configuration.MessageConfiguration;
import br.com.java.datacalculatefreight.exceptions.CustomException;
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
public class CountryStatesServiceTest {

    @InjectMocks
    CountryStatesService countryStatesService;

    @Mock
    MessageConfiguration messageConfiguration;

    @Mock
    GenericValidations genericValidations;

    @Mock
    CountryStatesRepository countryStatesRepository;

    @Test
    @DisplayName("Deve retornar erro quando não for localizado o estado por id")
    public void shouldReturnErrorWhenNotFoundStateById(){
        final Long id = 1l;
        final Optional<CountryStatesEntity> optionalCountryStatesEntity = Optional.empty();
        when(countryStatesRepository.findById(id)).thenReturn(optionalCountryStatesEntity);
        assertThrows(CustomException.class, () -> countryStatesService.getById(id));
    }

    @Test
    @DisplayName("Não deve retornar erro quando o registro for encontrado")
    public void shouldNotReturnErrorWhatTheRegistryWasFound() {
        final Long id = 1l;
        final CountryStatesEntity countryStatesEntity = CountryStatesEntityBuilder.getBasicCountryStatesEntity(id).getCountryStatesEntity();
        final Optional<CountryStatesEntity> optionalCountryStatesEntity = Optional.of(countryStatesEntity);
        when(countryStatesRepository.findById(id)).thenReturn(optionalCountryStatesEntity);
        compare(countryStatesEntity, assertDoesNotThrow(() -> countryStatesService.getById(id)));
    }

    private void compare(final CountryStatesEntity countryStatesEntity, final CountryStatesResponse countryStatesResponse) {
        assertNotNull(countryStatesEntity);
        assertNotNull(countryStatesResponse);
        assertEquals(countryStatesEntity.getId(), countryStatesResponse.getId());
        assertEquals(countryStatesEntity.getState(), countryStatesResponse.getState());
        assertEquals(countryStatesEntity.getStateCode(), countryStatesResponse.getStateCode());
        assertEquals(countryStatesEntity.getDateCreate(), countryStatesResponse.getDateCreate());
    }

    @Test
    @DisplayName("Deve retornar erro quando não localizado nenhum estado por uf")
    public void shouldReturnErrorWhenNotLocatedNoStateByCode() {
        final String stateCode = "AB";
        final CountryStatesEntity countryStatesEntity = null;
        when(countryStatesRepository.findByStateCode(stateCode)).thenReturn(countryStatesEntity);
        assertThrows(CustomException.class, () -> countryStatesService.getByStateCode(stateCode));
    }

    @Test
    @DisplayName("Deve retornar erro quando uf for nulo")
    public void shouldReturnErrorWhenStateCodeWasNull() {
        final String stateCode = null;
        assertThrows(CustomException.class, () -> countryStatesService.getByStateCode(stateCode));
    }

    @Test
    @DisplayName("Deve retornar erro quando uf for vazio")
    public void shouldReturnErrorWhenStateCodeWasEmpty() {
        final String stateCode = " ";
        assertThrows(CustomException.class, () -> countryStatesService.getByStateCode(stateCode));
    }

    @Test
    @DisplayName("Deve retornar erro quando uf tiver 1 caracter")
    public void shouldReturnErrorWhenStateCodeHadOneCharacter() {
        final String stateCode = "A";
        assertThrows(CustomException.class, () -> countryStatesService.getByStateCode(stateCode));
    }

    @Test
    @DisplayName("Deve retornar erro quando uf tiver 3 caracter")
    public void shouldReturnErrorWhenStateCodeHadThreeCharacter() {
        final String stateCode = "Abc";
        assertThrows(CustomException.class, () -> countryStatesService.getByStateCode(stateCode));
    }

    @Test
    @DisplayName("Não deve retornar erro quando localizado estado por uf")
    public void shouldNotReturnErrorWhenLocatedStateByCode() {
        final String stateCode = "ac ";
        final CountryStatesEntity countryStatesEntity = CountryStatesEntityBuilder.getBasicCountryStatesEntity().getCountryStatesEntity();
        when(countryStatesRepository.findByStateCode(stateCode.toUpperCase().trim())).thenReturn(countryStatesEntity);
        compare(countryStatesEntity, assertDoesNotThrow(() -> countryStatesService.getByStateCode(stateCode)));
    }
}
