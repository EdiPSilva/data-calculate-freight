package br.com.java.datacalculatefreight.application.company;

import br.com.java.datacalculatefreight.application.company.builder.CompanyEntityBuilder;
import br.com.java.datacalculatefreight.application.company.persistence.CompanyEntity;
import br.com.java.datacalculatefreight.application.company.persistence.CompanyRepository;
import br.com.java.datacalculatefreight.application.company.resource.CompanyResponse;
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
public class CompanyServiceTest {

    @InjectMocks
    CompanyService companyService;

    @Mock
    MessageConfiguration messageConfiguration;

    @Mock
    GenericValidations genericValidations;

    @Mock
    CompanyRepository companyRepository;

    @Test
    @DisplayName("Deve retornar erro quando não for localizado a empresa por id")
    public void shouldReturnErrorWhenNotFoundCompanyById(){
        final Long id = 1l;
        final Optional<CompanyEntity> optionalCompanyEntity = Optional.empty();
        when(companyRepository.findById(id)).thenReturn(optionalCompanyEntity);
        assertThrows(CustomException.class, () -> companyService.getById(id));
    }

    @Test
    @DisplayName("Não deve retornar erro quando o registro for encontrado")
    public void shouldNotReturnErrorWhatTheRegistryWasFound() {
        final Long id = 1l;
        final var companyEntity = CompanyEntityBuilder.getBasicCompanyEntity(id).getCompanyEntity();
        final Optional<CompanyEntity> optionalCompanyEntity = Optional.of(companyEntity);
        when(companyRepository.findById(id)).thenReturn(optionalCompanyEntity);
        final var companyEntityAuxiliary = assertDoesNotThrow(() -> companyService.getById(id));
        compareCompanyEntity(companyEntity, companyEntityAuxiliary);
    }

    private void compareCompanyEntity(final CompanyEntity companyEntity, final CompanyResponse companyResponse) {
        assertNotNull(companyEntity);
        assertNotNull(companyResponse);
        assertEquals(companyEntity.getId(), companyResponse.getId());
        assertEquals(companyEntity.getName(), companyResponse.getName());
        assertEquals(companyEntity.getDocument(), companyResponse.getDocument());
        assertEquals(companyEntity.getPostalCode(), companyResponse.getPostalCode());
        assertEquals(companyEntity.getActive(), companyResponse.getActive());
        assertEquals(companyEntity.getDateCreate(), companyResponse.getDateCreate());
    }
}
