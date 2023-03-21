package br.com.java.datacalculatefreight.utils;

import br.com.java.datacalculatefreight.configuration.MessageCodeEnum;
import br.com.java.datacalculatefreight.configuration.MessageConfiguration;
import br.com.java.datacalculatefreight.exceptions.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class GenericValidationsTest {

    @InjectMocks
    GenericValidations genericValidations;

    @Mock
    MessageConfiguration messageConfiguration;

    @Test
    @DisplayName("Não deve retornar erro quando o valor for maior que zero")
    public void shouldNotReturnErrorWhenValueWasBiggerThenZero() {
        final Long value = 1l;
        final MessageCodeEnum messageCodeEnum = MessageCodeEnum.INVALID_ID;
        assertDoesNotThrow(() -> genericValidations.validatevalidateNumberGreaterThanZero(value, messageCodeEnum));
    }

    @Test
    @DisplayName("Deve retornar erro quando MessageCodeEnum for null")
    public void shouldReturnErrorWhenMessageCodeEnumWillNull() {
        final Long value = 1l;
        final MessageCodeEnum messageCodeEnum = null;
        assertThrows(NullPointerException.class, () -> genericValidations.validatevalidateNumberGreaterThanZero(value, messageCodeEnum));
    }

    @Test
    @DisplayName("Deve retornar erro quando o valor for menor que zero")
    public void shouldReturnErrorWhenTheValueLessThanZero() {
        final Long value = -1l;
        final MessageCodeEnum messageCodeEnum = MessageCodeEnum.INVALID_ID;
        assertThrows(CustomException.class, () -> genericValidations.validatevalidateNumberGreaterThanZero(value, messageCodeEnum));
    }

    @Test
    @DisplayName("Deve retornar erro quando o valor for igual a zero")
    public void shouldReturnErrorWhenTheValueEqualsZero() {
        final Long value = 0l;
        final MessageCodeEnum messageCodeEnum = MessageCodeEnum.INVALID_ID;
        assertThrows(CustomException.class, () -> genericValidations.validatevalidateNumberGreaterThanZero(value, messageCodeEnum));
    }

    @Test
    @DisplayName("Deve retornar erro quando o valor for igual a null")
    public void shouldReturnErrorWhenTheValueEqualsNull() {
        final Long value = null;
        final MessageCodeEnum messageCodeEnum = MessageCodeEnum.INVALID_ID;
        assertThrows(CustomException.class, () -> genericValidations.validatevalidateNumberGreaterThanZero(value, messageCodeEnum));
    }

    @Test
    @DisplayName("Deve retornar erro quando o documento for invalido")
    public void shouldReturnErrorWhenTheDocumentWasInvalid() {
        final List<String> listInvalidCnpj = Arrays.asList("00000000000000","11111111111111","22222222222222","33333333333333","44444444444444","55555555555555","66666666666666","77777777777777","88888888888888","99999999999999", "44774021000131", "4477402100013", "447740210001301");
        listInvalidCnpj.forEach(invalidDocument -> {
            assertThrows(CustomException.class, () -> genericValidations.cnpjIsValid(invalidDocument));
        });
    }

    @Test
    @DisplayName("Não deve retornar erro quando o documento for válido")
    public void shouldNotReturnErrorWhenTheDocumentWasValid() {
        final String document = "44774021000130";
        final boolean valid = assertDoesNotThrow(() -> genericValidations.cnpjIsValid(document));
        assertTrue(valid);
    }
}
