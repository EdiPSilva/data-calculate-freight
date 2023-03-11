package br.com.java.datacalculatefreight.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
public class ErrorResponse {

    private final String message;
    private final int code;
    private final String status;
    private final List<ErrorObject> errors;
}
