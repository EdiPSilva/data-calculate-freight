package br.com.java.datacalculatefreight.application.typeDelivery.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeDeliveryRepository extends JpaRepository<TypeDeliveryEntity, Long> {
}
