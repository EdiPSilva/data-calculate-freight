package br.com.java.datacalculatefreight.application.typeDelivery;

import br.com.java.datacalculatefreight.application.typeDelivery.builders.TypeDeliveryEntityBuilder;
import br.com.java.datacalculatefreight.application.typeDelivery.builders.TypeDeliveryRequestBuilder;
import br.com.java.datacalculatefreight.application.typeDelivery.persistence.TypeDeliveryEntity;
import br.com.java.datacalculatefreight.application.typeDelivery.resources.TypeDeliveryRequest;
import br.com.java.datacalculatefreight.application.typeDelivery.resources.TypeDeliveryResponse;
import br.com.java.datacalculatefreight.application.typeDelivery.persistence.TypeDeliveryRepository;
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
public class TypeDeliveryServiceTest {

    @InjectMocks
    TypeDeliveryService typeDeliveryService;

    @Mock
    MessageConfiguration messageConfiguration;

    @Mock
    GenericValidations genericValidations;

    @Mock
    TypeDeliveryRepository typeDeliveryRepository;

    @Test
    @DisplayName("Deve retornar erro quando não for localizado o tipo entrega por id")
    public void shouldReturnErrorWhenNotFoundTypeDeliveryById(){
        final Long id = 1l;
        final Optional<TypeDeliveryEntity> optionalTypeDeliveryEntity = Optional.empty();
        when(typeDeliveryRepository.findById(id)).thenReturn(optionalTypeDeliveryEntity);
        assertThrows(CustomException.class, () -> typeDeliveryService.getById(id));
    }

    @Test
    @DisplayName("Não deve retornar erro quando o registro for encontrado")
    public void shouldNotReturnErrorWhatTheRegistryWasFound() {
        final Long id = 1l;
        final TypeDeliveryEntity typeDeliveryEntity = TypeDeliveryEntityBuilder.getBasicTypeDeliveryEntity(id).getTypeDeliveryEntity();
        final Optional<TypeDeliveryEntity> optionalTypeDeliveryEntity = Optional.of(typeDeliveryEntity);
        when(typeDeliveryRepository.findById(id)).thenReturn(optionalTypeDeliveryEntity);
        compare(typeDeliveryEntity, assertDoesNotThrow(() -> typeDeliveryService.getById(id)));
    }

    private void compare(final TypeDeliveryEntity typeDeliveryEntity, final TypeDeliveryResponse typeDeliveryResponse) {
        assertNotNull(typeDeliveryEntity);
        assertNotNull(typeDeliveryResponse);
        assertEquals(typeDeliveryEntity.getId(), typeDeliveryResponse.getId());
        assertEquals(typeDeliveryEntity.getType(), typeDeliveryResponse.getType());
        assertEquals(typeDeliveryEntity.getActive(), typeDeliveryResponse.getActive());
        assertEquals(typeDeliveryEntity.getDateCreate(), typeDeliveryResponse.getDateCreate());
    }

    @Test
    @DisplayName("Deve retornar erro quando não localizado nenhum tipo entrega por tipo")
    public void shouldReturnErrorWhenNotLocatedNoTypeDeliveryByType() {
        final String type = "EXPRESS";
        final TypeDeliveryEntity typeDeliveryEntity = null;
        when(typeDeliveryRepository.findByType(type)).thenReturn(typeDeliveryEntity);
        assertThrows(CustomException.class, () -> typeDeliveryService.getByType(type));
    }

    @Test
    @DisplayName("Não deve retornar erro quando localizado tipo entrega por tipo")
    public void shouldNotReturnErrorWhenLocatedTypeDeliveryByType() {
        final String type = "EXPRESS";
        final TypeDeliveryEntity typeDeliveryEntity = TypeDeliveryEntityBuilder.getBasicTypeDeliveryEntity().getTypeDeliveryEntity();
        when(typeDeliveryRepository.findByType(type)).thenReturn(typeDeliveryEntity);
        compare(typeDeliveryEntity, assertDoesNotThrow(() -> typeDeliveryService.getByType(type)));
    }

    @Test
    @DisplayName("Deve retornar erro quando tipo já existir")
    public void shouldReturnErrorWhenTypeAlreadyExists() {
        final TypeDeliveryRequest typeDeliveryRequest = TypeDeliveryRequestBuilder.getBasicTypeDeliveryRequest().getTypeDeliveryRequest();
        final Long existingId = 1l;
        when(typeDeliveryRepository.findTypeDeliveryEntityByType(typeDeliveryRequest.getType())).thenReturn(existingId);
        assertThrows(CustomException.class, () -> typeDeliveryService.create(typeDeliveryRequest));
    }

    @Test
    @DisplayName("Não deve retornar erro quando tipo não existir")
    public void shouldNotReturnErrorWhenTypeNotExists() {
        final TypeDeliveryRequest typeDeliveryRequest = TypeDeliveryRequestBuilder.getBasicTypeDeliveryRequest().getTypeDeliveryRequest();
        final TypeDeliveryEntity typeDeliveryEntity = TypeDeliveryEntityBuilder.getTypeDeliveryEntityByTypeDeliveryRequest(typeDeliveryRequest).getTypeDeliveryEntity();
        typeDeliveryEntity.setId(1l);
        final Long existingId = null;
        when(typeDeliveryRepository.findTypeDeliveryEntityByType(typeDeliveryRequest.getType())).thenReturn(existingId);
        when(typeDeliveryRepository.save(Mockito.any())).thenReturn(typeDeliveryEntity);
        compare(typeDeliveryRequest, assertDoesNotThrow(() -> typeDeliveryService.create(typeDeliveryRequest)));
    }

    private void compare(final TypeDeliveryRequest typeDeliveryRequest, final TypeDeliveryResponse typeDeliveryResponse) {
        assertNotNull(typeDeliveryRequest);
        assertNotNull(typeDeliveryResponse);
        assertNotNull(typeDeliveryResponse.getId());
        assertEquals(typeDeliveryRequest.getType(), typeDeliveryResponse.getType());
        assertEquals(typeDeliveryRequest.getActive(), typeDeliveryResponse.getActive());
        assertNotNull(typeDeliveryResponse.getDateCreate());
    }

    @Test
    @DisplayName("Deve retornar erro quando não encontrado tipo entrega por tipo durante a atualização")
    public void shouldReturnErrorWhenNotFoundTypeDeliveryByTypeInRegistryUpdate() {
        final Long id = 1l;
        final TypeDeliveryRequest typeDeliveryRequest = TypeDeliveryRequestBuilder.getBasicTypeDeliveryRequest().getTypeDeliveryRequest();
        final Long existingId = null;
        when(typeDeliveryRepository.findTypeDeliveryEntityByType(typeDeliveryRequest.getType())).thenReturn(existingId);
        assertThrows(CustomException.class, () -> typeDeliveryService.update(id, typeDeliveryRequest));
    }

    @Test
    @DisplayName("Deve retornar erro quando encontrado tipo entrega por tipo com outro id durante a atualização")
    public void shouldReturnErrorWhenFoundTypeDeliveryByTypeWithOtherIdInRegistryUpdate() {
        final Long id = 1l;
        final TypeDeliveryRequest typeDeliveryRequest = TypeDeliveryRequestBuilder.getBasicTypeDeliveryRequest().getTypeDeliveryRequest();
        final Long existingId = 2l;
        when(typeDeliveryRepository.findTypeDeliveryEntityByType(typeDeliveryRequest.getType())).thenReturn(existingId);
        assertThrows(CustomException.class, () -> typeDeliveryService.update(id, typeDeliveryRequest));
    }

    @Test
    @DisplayName("Não deve retornar erro quando encontrado tipo entrega por tipo durante a atualização")
    public void shouldNotReturnErrorWhenFoundTypeDeliveryByTypeInRegistryUpdate() {
        final TypeDeliveryRequest typeDeliveryRequest = TypeDeliveryRequestBuilder.getBasicTypeDeliveryRequest().getTypeDeliveryRequest();

        final Long id = 1l;
        final Long existingId = id;
        when(typeDeliveryRepository.findTypeDeliveryEntityByType(typeDeliveryRequest.getType())).thenReturn(existingId);

        final TypeDeliveryEntity typeDeliveryEntity = TypeDeliveryEntityBuilder.getTypeDeliveryEntityByTypeDeliveryRequest(typeDeliveryRequest).getTypeDeliveryEntity();
        typeDeliveryEntity.setId(1l);
        final Optional<TypeDeliveryEntity> optionalTypeDeliveryEntity = Optional.of(typeDeliveryEntity);
        when(typeDeliveryRepository.findById(id)).thenReturn(optionalTypeDeliveryEntity);
        when(typeDeliveryRepository.save(Mockito.any())).thenReturn(typeDeliveryEntity);

        compare(typeDeliveryRequest, assertDoesNotThrow(() -> typeDeliveryService.update(id, typeDeliveryRequest)));
    }

    @Test
    @DisplayName("Deve retornar erro quando não encontrado transportadora por id durante a exclusão")
    public void shouldReturnErrorWhenNotFoundTypeDeliveryByIdInRegistryDelete() {
        final Long id = 1l;
        final Optional<TypeDeliveryEntity> optionalTypeDeliveryEntity = Optional.empty();
        when(typeDeliveryRepository.findById(id)).thenReturn(optionalTypeDeliveryEntity);
        assertThrows(CustomException.class, () -> typeDeliveryService.delete(id));
    }

    @Test
    @DisplayName("Não deve retornar erro quando encontrado empresa por id durante a exclusão")
    public void shouldNotReturnErrorWhenFoundCompanyByIdInRegistryDelete() {
        final Long id = 1l;
        final TypeDeliveryEntity typeDeliveryEntity = TypeDeliveryEntityBuilder.getBasicTypeDeliveryEntity(id).getTypeDeliveryEntity();
        final Optional<TypeDeliveryEntity> optionalTypeDeliveryEntity = Optional.of(typeDeliveryEntity);
        when(typeDeliveryRepository.findById(id)).thenReturn(optionalTypeDeliveryEntity);

        final DefaultResponse defaultResponse = assertDoesNotThrow(() -> typeDeliveryService.delete(id));
        assertNotNull(defaultResponse);
        assertNotNull(defaultResponse.getObject());
        assertEquals(StatusMessageEnum.SUCCESS.getValue(), defaultResponse.getStatus());

        final TypeDeliveryResponse typeDeliveryResponse = (TypeDeliveryResponse) defaultResponse.getObject();

        assertEquals(typeDeliveryEntity.getId(), typeDeliveryResponse.getId());
        assertEquals(typeDeliveryEntity.getType(), typeDeliveryResponse.getType());
        assertEquals(typeDeliveryEntity.getActive(), typeDeliveryResponse.getActive());
        assertEquals(typeDeliveryEntity.getDateCreate(), typeDeliveryResponse.getDateCreate());
    }
}
