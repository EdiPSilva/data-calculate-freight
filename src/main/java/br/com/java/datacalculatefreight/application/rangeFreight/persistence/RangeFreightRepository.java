package br.com.java.datacalculatefreight.application.rangeFreight.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RangeFreightRepository extends JpaRepository<RangeFreightEntity, Long> {
}
