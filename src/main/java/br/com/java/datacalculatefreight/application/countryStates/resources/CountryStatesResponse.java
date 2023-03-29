package br.com.java.datacalculatefreight.application.countryStates.resources;

import br.com.java.datacalculatefreight.application.countryStates.persistence.CountryStatesEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CountryStatesResponse {

    private CountryStatesResponse(CountryStatesEntity countryStatesEntity) {
        this.id = countryStatesEntity.getId();
        this.state = countryStatesEntity.getState();
        this.stateCode = countryStatesEntity.getStateCode();
        this.dateCreate = countryStatesEntity.getDateCreate();
    }

    @ApiModelProperty(notes = "Id do estado", example = "1", required = true)
    private Long id;

    @ApiModelProperty(notes = "Estado", example = "São Paulo", required = true)
    private String state;

    @ApiModelProperty(notes = "Sigla", example = "SP", required = true)
    private String stateCode;

    @ApiModelProperty(notes = "Data de cadastro", example = "dd/MM/yyyy hh:mm:ss", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime dateCreate;

    public static CountryStatesResponse from(CountryStatesEntity countryStatesEntity) {
        return new CountryStatesResponse(countryStatesEntity);
    }
}
