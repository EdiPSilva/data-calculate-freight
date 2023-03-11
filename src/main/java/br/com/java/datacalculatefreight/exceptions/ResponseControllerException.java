package br.com.java.datacalculatefreight.exceptions;
import br.com.java.datacalculatefreight.configuration.MessageCodeEnum;
import br.com.java.datacalculatefreight.configuration.MessageConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;

@ControllerAdvice
public class ResponseControllerException {

    @Autowired
    MessageConfiguration messageConfiguration;

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomExceptions(RuntimeException runtimeException) {
        String mensagem = runtimeException.getMessage();//TODO - adequar uso da mensagem no objeto ErrorObject

        return new ResponseEntity<>(ErrorResponse.builder()
                .message(messageConfiguration.getMessageByCode(MessageCodeEnum.INVALID_REQUEST_DEFAULT_MESSAGE))
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .errors(Arrays.asList())
                .build(),
                HttpStatus.BAD_REQUEST);
    }
}
