package br.com.java.datacalculatefreight.application.company;

import br.com.java.datacalculatefreight.application.company.builder.CompanyEntityBuilder;
import br.com.java.datacalculatefreight.application.company.builder.CompanyRequestBuilder;
import br.com.java.datacalculatefreight.application.company.persistence.CompanyEntity;
import br.com.java.datacalculatefreight.application.company.persistence.CompanyRepository;
import br.com.java.datacalculatefreight.application.company.resources.CompanyRequest;
import br.com.java.datacalculatefreight.application.company.resources.CompanyResponse;
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
        final CompanyEntity companyEntity = CompanyEntityBuilder.getBasicCompanyEntity(id).getCompanyEntity();
        final Optional<CompanyEntity> optionalCompanyEntity = Optional.of(companyEntity);
        when(companyRepository.findById(id)).thenReturn(optionalCompanyEntity);
        compare(companyEntity, assertDoesNotThrow(() -> companyService.getById(id)));
    }

    private void compare(final CompanyEntity companyEntity, final CompanyResponse companyResponse) {
        assertNotNull(companyEntity);
        assertNotNull(companyResponse);
        assertEquals(companyEntity.getId(), companyResponse.getId());
        assertEquals(companyEntity.getName(), companyResponse.getName());
        assertEquals(companyEntity.getDocument(), companyResponse.getDocument());
        assertEquals(companyEntity.getPostalCode(), companyResponse.getPostalCode());
        assertEquals(companyEntity.getActive(), companyResponse.getActive());
        assertEquals(companyEntity.getDateCreate(), companyResponse.getDateCreate());
    }

    @Test
    @DisplayName("Deve retornar erro quando não localizado nenhuma empresa por documento")
    public void shouldReturnErrorWhenNotLocatedNoCompanyByDocument() {
        final String document = "33348219000135";
        final CompanyEntity companyEntity = null;
        when(companyRepository.findByDocument(document)).thenReturn(companyEntity);
        assertThrows(CustomException.class, () -> companyService.getByDocument(document));
    }

    @Test
    @DisplayName("Não deve retornar erro quando localizado empresa por documento")
    public void shouldNotReturnErrorWhenLocatedCompanyByDocument() {
        final String document = "33662514000161";
        final CompanyEntity companyEntity = CompanyEntityBuilder.getBasicCompanyEntity().getCompanyEntity();
        when(companyRepository.findByDocument(document)).thenReturn(companyEntity);
        compare(companyEntity, assertDoesNotThrow(() -> companyService.getByDocument(document)));
    }

    @Test
    @DisplayName("Deve retornar erro quando documento já existir")
    public void shouldReturnErrorWhenDocumentAlreadyExists() {
        final CompanyRequest companyRequest = CompanyRequestBuilder.getBasicCompanyRequest().getCompanyRequest();
        final Long existingId = 1l;
        when(companyRepository.findCompanyEntityByDocument(companyRequest.getDocument())).thenReturn(existingId);
        assertThrows(CustomException.class, () -> companyService.create(companyRequest));
    }

    @Test
    @DisplayName("Não deve retornar erro quando documento não existir")
    public void shouldNotReturnErrorWhenDocumentNotExists() {
        final CompanyRequest companyRequest = CompanyRequestBuilder.getBasicCompanyRequest().getCompanyRequest();
        final CompanyEntity companyEntity = CompanyEntityBuilder.getCompanyEntityByCompanyRequest(companyRequest).getCompanyEntity();
        companyEntity.setId(1l);
        final Long existingId = null;
        when(companyRepository.findCompanyEntityByDocument(companyRequest.getDocument())).thenReturn(existingId);
        when(companyRepository.save(Mockito.any())).thenReturn(companyEntity);
        compare(companyRequest, assertDoesNotThrow(() -> companyService.create(companyRequest)));
    }

    private void compare(final CompanyRequest companyRequest, final CompanyResponse companyResponse) {
        assertNotNull(companyRequest);
        assertNotNull(companyResponse);
        assertNotNull(companyResponse.getId());
        assertEquals(companyRequest.getName(), companyResponse.getName());
        assertEquals(companyRequest.getDocument(), companyResponse.getDocument());
        assertEquals(companyRequest.getPostalCode(), companyResponse.getPostalCode());
        assertEquals(companyRequest.getActive(), companyResponse.getActive());
        assertNotNull(companyResponse.getDateCreate());
    }

    @Test
    @DisplayName("Deve retornar erro quando não encontrado empresa por documento durante a atualização")
    public void shouldReturnErrorWhenNotFoundCompanyByDocumentInRegistryUpdate() {
        final Long id = 1l;
        final CompanyRequest companyRequest = CompanyRequestBuilder.getBasicCompanyRequest().getCompanyRequest();
        final Long existingId = null;
        when(companyRepository.findCompanyEntityByDocument(companyRequest.getDocument())).thenReturn(existingId);
        assertThrows(CustomException.class, () -> companyService.update(id, companyRequest));
    }

    @Test
    @DisplayName("Deve retornar erro quando encontrado empresa por documento com outro id durante a atualização")
    public void shouldReturnErrorWhenFoundCompanyByDocumentWithOtherIdInRegistryUpdate() {
        final Long id = 1l;
        final CompanyRequest companyRequest = CompanyRequestBuilder.getBasicCompanyRequest().getCompanyRequest();
        final Long existingId = 2l;
        when(companyRepository.findCompanyEntityByDocument(companyRequest.getDocument())).thenReturn(existingId);
        assertThrows(CustomException.class, () -> companyService.update(id, companyRequest));
    }

    @Test
    @DisplayName("Não deve retornar erro quando encontrado empresa por documento durante a atualização")
    public void shouldNotReturnErrorWhenFoundCompanyByDocumentInRegistryUpdate() {
        final CompanyRequest companyRequest = CompanyRequestBuilder.getBasicCompanyRequest().getCompanyRequest();

        final Long id = 1l;
        final Long existingId = id;
        when(companyRepository.findCompanyEntityByDocument(companyRequest.getDocument())).thenReturn(existingId);

        final CompanyEntity companyEntity = CompanyEntityBuilder.getCompanyEntityByCompanyRequest(companyRequest).getCompanyEntity();
        companyEntity.setId(1l);
        final Optional<CompanyEntity> optionalCompanyEntity = Optional.of(companyEntity);
        when(companyRepository.findById(id)).thenReturn(optionalCompanyEntity);
        when(companyRepository.save(Mockito.any())).thenReturn(companyEntity);

        compare(companyRequest, assertDoesNotThrow(() -> companyService.update(id, companyRequest)));
    }

    @Test
    @DisplayName("Deve retornar erro quando não encontrado empresa por id durante a exclusão")
    public void shouldReturnErrorWhenNotFoundCompanyByIdInRegistryDelete() {
        final Long id = 1l;
        final Optional<CompanyEntity> optionalCompanyEntity = Optional.empty();
        when(companyRepository.findById(id)).thenReturn(optionalCompanyEntity);
        assertThrows(CustomException.class, () -> companyService.delete(id));
    }

    @Test
    @DisplayName("Não deve retornar erro quando encontrado empresa por id durante a exclusão")
    public void shouldNotReturnErrorWhenFoundCompanyByIdInRegistryDelete() {
        final Long id = 1l;
        final CompanyEntity companyEntity = CompanyEntityBuilder.getBasicCompanyEntity(id).getCompanyEntity();
        final Optional<CompanyEntity> optionalCompanyEntity = Optional.of(companyEntity);
        when(companyRepository.findById(id)).thenReturn(optionalCompanyEntity);

        final DefaultResponse defaultResponse = assertDoesNotThrow(() -> companyService.delete(id));
        assertNotNull(defaultResponse);
        assertNotNull(defaultResponse.getObject());
        assertEquals(StatusMessageEnum.SUCCESS.getValue(), defaultResponse.getStatus());

        final CompanyResponse companyResponse = (CompanyResponse) defaultResponse.getObject();

        assertEquals(companyEntity.getId(), companyResponse.getId());
        assertEquals(companyEntity.getName(), companyResponse.getName());
        assertEquals(companyEntity.getDocument(), companyResponse.getDocument());
        assertEquals(companyEntity.getPostalCode(), companyResponse.getPostalCode());
        assertEquals(companyEntity.getActive(), companyResponse.getActive());
        assertEquals(companyEntity.getDateCreate(), companyResponse.getDateCreate());
    }
}
