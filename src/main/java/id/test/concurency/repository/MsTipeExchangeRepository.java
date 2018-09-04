package id.test.concurency.repository;

import id.test.concurency.entity.MsTipeExchangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MsTipeExchangeRepository extends JpaRepository<MsTipeExchangeEntity, Long> {
    MsTipeExchangeEntity findByFromAndTo(String from, String to);
}
