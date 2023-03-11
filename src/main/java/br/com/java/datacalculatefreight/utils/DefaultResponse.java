package br.com.java.datacalculatefreight.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DefaultResponse {

    private String status;

    private Object object;
}
