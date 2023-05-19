package br.com.java.datacalculatefreight.application.shippingCompany;

import br.com.java.datacalculatefreight.application.shippingCompany.resources.ShippingCompanyRequest;
import br.com.java.datacalculatefreight.application.shippingCompany.resources.ShippingCompanyResponse;
import br.com.java.datacalculatefreight.application.shippingCompany.builders.ShippingCompanyEntityBuilder;
import br.com.java.datacalculatefreight.application.shippingCompany.builders.ShippingCompanyRequestBuilder;
import br.com.java.datacalculatefreight.application.shippingCompany.persistence.ShippingCompanyEntity;
import br.com.java.datacalculatefreight.application.shippingCompany.persistence.ShippingCompanyRepository;
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
public class ShippingCompanyServiceTest {

    @InjectMocks
    ShippingCompanyService shippingCompanyService;

    @Mock
    MessageConfiguration messageConfiguration;

    @Mock
    GenericValidations genericValidations;

    @Mock
    ShippingCompanyRepository shippingCompanyRepository;

    @Test
    @DisplayName("Deve retornar erro quando não for localizado a transportadora por id")
    public void shouldReturnErrorWhenNotFoundShippingCompanyById(){
        final Long id = 1l;
        final Optional<ShippingCompanyEntity> optionalShippingCompanyEntity = Optional.empty();
        when(shippingCompanyRepository.findById(id)).thenReturn(optionalShippingCompanyEntity);
        assertThrows(CustomException.class, () -> shippingCompanyService.getById(id));
    }

    @Test
    @DisplayName("Não deve retornar erro quando o registro for encontrado")
    public void shouldNotReturnErrorWhatTheRegistryWasFound() {
        final Long id = 1l;
        final ShippingCompanyEntity shippingCompanyEntity = ShippingCompanyEntityBuilder.getInstance(id).getShippingCompanyEntity();
        final Optional<ShippingCompanyEntity> optionalShippingCompanyEntity = Optional.of(shippingCompanyEntity);
        when(shippingCompanyRepository.findById(id)).thenReturn(optionalShippingCompanyEntity);
        compare(shippingCompanyEntity, assertDoesNotThrow(() -> shippingCompanyService.getById(id)));
    }

    private void compare(final ShippingCompanyEntity shippingCompanyEntity, final ShippingCompanyResponse shippingCompanyResponse) {
        assertNotNull(shippingCompanyEntity);
        assertNotNull(shippingCompanyResponse);
        assertEquals(shippingCompanyEntity.getId(), shippingCompanyResponse.getId());
        assertEquals(shippingCompanyEntity.getName(), shippingCompanyResponse.getName());
        assertEquals(shippingCompanyEntity.getDocument(), shippingCompanyResponse.getDocument());
        assertEquals(shippingCompanyEntity.getActive(), shippingCompanyResponse.getActive());
        assertEquals(shippingCompanyEntity.getDateCreate(), shippingCompanyResponse.getDateCreate());
    }

    @Test
    @DisplayName("Deve retornar erro quando não localizado nenhuma transportadora por documento")
    public void shouldReturnErrorWhenNotLocatedNoShippingCompanyByDocument() {
        final String document = "33348219000135";
        final ShippingCompanyEntity shippingCompanyEntity = null;
        when(shippingCompanyRepository.findByDocument(document)).thenReturn(shippingCompanyEntity);
        assertThrows(CustomException.class, () -> shippingCompanyService.getByDocument(document));
    }

    @Test
    @DisplayName("Não deve retornar erro quando localizado transportadora por documento")
    public void shouldNotReturnErrorWhenLocatedShippingCompanyByDocument() {
        final String document = "88449952000138";
        final ShippingCompanyEntity shippingCompanyEntity = ShippingCompanyEntityBuilder.getInstance().getShippingCompanyEntity();
        when(shippingCompanyRepository.findByDocument(document)).thenReturn(shippingCompanyEntity);
        compare(shippingCompanyEntity, assertDoesNotThrow(() -> shippingCompanyService.getByDocument(document)));
    }

    @Test
    @DisplayName("Deve retornar erro quando documento já existir")
    public void shouldReturnErrorWhenDocumentAlreadyExists() {
        final ShippingCompanyRequest shippingCompanyRequest = ShippingCompanyRequestBuilder.getInstance().getShippingCompanyRequest();
        final Long existingId = 1l;
        when(shippingCompanyRepository.findShippingCompanyEntityByDocument(shippingCompanyRequest.getDocument())).thenReturn(existingId);
        assertThrows(CustomException.class, () -> shippingCompanyService.create(shippingCompanyRequest));
    }

    @Test
    @DisplayName("Não deve retornar erro quando documento não existir")
    public void shouldNotReturnErrorWhenDocumentNotExists() {
        final ShippingCompanyRequest shippingCompanyRequest = ShippingCompanyRequestBuilder.getInstance().getShippingCompanyRequest();
        final ShippingCompanyEntity shippingCompanyEntity = ShippingCompanyEntityBuilder.getInstance(shippingCompanyRequest).getShippingCompanyEntity();
        shippingCompanyEntity.setId(1l);
        final Long existingId = null;
        when(shippingCompanyRepository.findShippingCompanyEntityByDocument(shippingCompanyRequest.getDocument())).thenReturn(existingId);
        when(shippingCompanyRepository.save(Mockito.any())).thenReturn(shippingCompanyEntity);
        compare(shippingCompanyRequest, assertDoesNotThrow(() -> shippingCompanyService.create(shippingCompanyRequest)));
    }

    private void compare(final ShippingCompanyRequest shippingCompanyRequest, final ShippingCompanyResponse shippingCompanyResponse) {
        assertNotNull(shippingCompanyRequest);
        assertNotNull(shippingCompanyResponse);
        assertNotNull(shippingCompanyResponse.getId());
        assertEquals(shippingCompanyRequest.getName(), shippingCompanyResponse.getName());
        assertEquals(shippingCompanyRequest.getDocument(), shippingCompanyResponse.getDocument());
        assertEquals(shippingCompanyRequest.getActive(), shippingCompanyResponse.getActive());
        assertNotNull(shippingCompanyResponse.getDateCreate());
    }

    @Test
    @DisplayName("Deve retornar erro quando não encontrado transportadora por documento durante a atualização")
    public void shouldReturnErrorWhenNotFoundShippingCompanyByDocumentInRegistryUpdate() {
        final Long id = 1l;
        final ShippingCompanyRequest shippingCompanyRequest = ShippingCompanyRequestBuilder.getInstance().getShippingCompanyRequest();
        final Long existingId = null;
        when(shippingCompanyRepository.findShippingCompanyEntityByDocument(shippingCompanyRequest.getDocument())).thenReturn(existingId);
        assertThrows(CustomException.class, () -> shippingCompanyService.update(id, shippingCompanyRequest));
    }

    @Test
    @DisplayName("Deve retornar erro quando encontrado transportadora por documento com outro id durante a atualização")
    public void shouldReturnErrorWhenFoundShippingCompanyByDocumentWithOtherIdInRegistryUpdate() {
        final Long id = 1l;
        final ShippingCompanyRequest shippingCompanyRequest = ShippingCompanyRequestBuilder.getInstance().getShippingCompanyRequest();
        final Long existingId = 2l;
        when(shippingCompanyRepository.findShippingCompanyEntityByDocument(shippingCompanyRequest.getDocument())).thenReturn(existingId);
        assertThrows(CustomException.class, () -> shippingCompanyService.update(id, shippingCompanyRequest));
    }

    @Test
    @DisplayName("Não deve retornar erro quando encontrado transportadora por documento durante a atualização")
    public void shouldNotReturnErrorWhenFoundShippingCompanyByDocumentInRegistryUpdate() {
        final ShippingCompanyRequest shippingCompanyRequest = ShippingCompanyRequestBuilder.getInstance().getShippingCompanyRequest();

        final Long id = 1l;
        final Long existingId = id;
        when(shippingCompanyRepository.findShippingCompanyEntityByDocument(shippingCompanyRequest.getDocument())).thenReturn(existingId);

        final ShippingCompanyEntity shippingCompanyEntity = ShippingCompanyEntityBuilder.getInstance(shippingCompanyRequest).getShippingCompanyEntity();
        shippingCompanyEntity.setId(1l);
        final Optional<ShippingCompanyEntity> optionalShippingCompanyEntity = Optional.of(shippingCompanyEntity);
        when(shippingCompanyRepository.findById(id)).thenReturn(optionalShippingCompanyEntity);
        when(shippingCompanyRepository.save(Mockito.any())).thenReturn(shippingCompanyEntity);

        compare(shippingCompanyRequest, assertDoesNotThrow(() -> shippingCompanyService.update(id, shippingCompanyRequest)));
    }

    @Test
    @DisplayName("Deve retornar erro quando não encontrado transportadora por id durante a exclusão")
    public void shouldReturnErrorWhenNotFoundShippingCompanyByIdInRegistryDelete() {
        final Long id = 1l;
        final Optional<ShippingCompanyEntity> optionalShippingCompanyEntity = Optional.empty();
        when(shippingCompanyRepository.findById(id)).thenReturn(optionalShippingCompanyEntity);
        assertThrows(CustomException.class, () -> shippingCompanyService.delete(id));
    }

    @Test
    @DisplayName("Não deve retornar erro quando encontrado transportadora por id durante a exclusão")
    public void shouldNotReturnErrorWhenFoundShippingCompanyByIdInRegistryDelete() {
        final Long id = 1l;
        final ShippingCompanyEntity shippingCompanyEntity = ShippingCompanyEntityBuilder.getInstance(id).getShippingCompanyEntity();
        final Optional<ShippingCompanyEntity> optionalShippingCompanyEntity = Optional.of(shippingCompanyEntity);
        when(shippingCompanyRepository.findById(id)).thenReturn(optionalShippingCompanyEntity);

        final DefaultResponse defaultResponse = assertDoesNotThrow(() -> shippingCompanyService.delete(id));
        assertNotNull(defaultResponse);
        assertNotNull(defaultResponse.getObject());
        assertEquals(StatusMessageEnum.SUCCESS.getValue(), defaultResponse.getStatus());

        final ShippingCompanyResponse shippingCompanyResponse = (ShippingCompanyResponse) defaultResponse.getObject();

        assertEquals(shippingCompanyEntity.getId(), shippingCompanyResponse.getId());
        assertEquals(shippingCompanyEntity.getName(), shippingCompanyResponse.getName());
        assertEquals(shippingCompanyEntity.getDocument(), shippingCompanyResponse.getDocument());
        assertEquals(shippingCompanyEntity.getActive(), shippingCompanyResponse.getActive());
        assertEquals(shippingCompanyEntity.getDateCreate(), shippingCompanyResponse.getDateCreate());
    }
}
