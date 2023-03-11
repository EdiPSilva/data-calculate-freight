package br.com.java.datacalculatefreight.application.company.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {

    public CompanyEntity findByDocument(String document);

    @Query("select c.id from CompanyEntity c where c.document = :document")
    public Long findCompanyEntityByDocument(@Param("document") String document);

}
