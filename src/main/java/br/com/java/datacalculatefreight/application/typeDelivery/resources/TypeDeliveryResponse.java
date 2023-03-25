package br.com.java.datacalculatefreight.application.typeDelivery.resources;

import br.com.java.datacalculatefreight.application.typeDelivery.persistence.TypeDeliveryEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TypeDeliveryResponse {

    private TypeDeliveryResponse (TypeDeliveryEntity typeDeliveryEntity) {
        this.id = typeDeliveryEntity.getId();
        this.type = typeDeliveryEntity.getType();
        this.active = typeDeliveryEntity.getActive();
        this.dateCreate = typeDeliveryEntity.getDateCreate();
        this.dateUpdate = typeDeliveryEntity.getDateUpdate();
    }

    @ApiModelProperty(notes = "Id", example = "1", required = true)
    private Long id;

    @ApiModelProperty(notes = "Tipo entrega", example = "SEDEX", required = true)
    private String type;

    @ApiModelProperty(notes = "Status", example = "true", required = true)
    private Boolean active;

    @ApiModelProperty(notes = "Data de cadastro", example = "dd/MM/yyyy hh:mm:ss", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime dateCreate;

    @ApiModelProperty(notes = "Data de atualização", example = "dd/MM/yyyy hh:mm:ss", required = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime dateUpdate;

    public static TypeDeliveryResponse from(TypeDeliveryEntity typeDeliveryEntity) {
        return new TypeDeliveryResponse(typeDeliveryEntity);
    }
}
