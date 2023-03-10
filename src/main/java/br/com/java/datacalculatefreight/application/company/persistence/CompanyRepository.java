package br.com.java.datacalculatefreight.application.company.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {

    public CompanyEntity findByDocument(String document);
}
