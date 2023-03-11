package br.com.java.datacalculatefreight.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorObject {

    private final String message;
    private final String field;
    private final Object parameter;
}
