package br.com.java.datacalculatefreight.application.countryStates.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.ReadOnlyProperty;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "country_states")
public class CountryStatesEntity {

    @Id
    @Column(columnDefinition = "serial")
    @ReadOnlyProperty
    private Long id;

    @Column(name = "state")
    @ReadOnlyProperty
    private String state;

    @Column(name = "state_code")
    @ReadOnlyProperty
    private String stateCode;

    @Column(name = "date_create")
    @ReadOnlyProperty
    private LocalDateTime dateCreate;
}
