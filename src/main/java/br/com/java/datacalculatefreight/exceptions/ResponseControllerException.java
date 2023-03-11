package br.com.java.datacalculatefreight.exceptions;
import br.com.java.datacalculatefreight.configuration.MessageCodeEnum;
import br.com.java.datacalculatefreight.configuration.MessageConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@ControllerAdvice
public class ResponseControllerException {

    @Autowired
    MessageConfiguration messageConfiguration;

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomExceptions(RuntimeException runtimeException) {
        log.error(runtimeException.getMessage());
        return buildResponseEntity(runtimeException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInternalServerError(Exception exception) {
        log.error(exception.getMessage());
        exception.printStackTrace();
        return buildResponseEntity(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> buildResponseEntity(String message, HttpStatus httpStatus) {
        return new ResponseEntity<>(ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .message(messageConfiguration.getMessageByCode(MessageCodeEnum.INVALID_REQUEST_DEFAULT_MESSAGE))
                .code(httpStatus.value())
                .status(httpStatus.getReasonPhrase())
                .errors(Arrays.asList(ErrorObject.builder().message(message).build()))
                .build(),
                httpStatus);
    }



}
