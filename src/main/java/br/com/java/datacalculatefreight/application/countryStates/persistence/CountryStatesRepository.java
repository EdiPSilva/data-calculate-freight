package br.com.java.datacalculatefreight.application.countryStates.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryStatesRepository extends JpaRepository<CountryStatesEntity, Long> {

    public CountryStatesEntity findByStateCode(String uf);
}
