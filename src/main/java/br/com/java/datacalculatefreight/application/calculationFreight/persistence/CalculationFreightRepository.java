package br.com.java.datacalculatefreight.application.calculationFreight.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalculationFreightRepository extends JpaRepository<CalculationFreightEntity, Long> {
}
