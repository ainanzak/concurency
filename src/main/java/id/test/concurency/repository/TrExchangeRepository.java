package id.test.concurency.repository;

import id.test.concurency.entity.TrExchangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TrExchangeRepository extends JpaRepository<TrExchangeEntity, Long> {
    TrExchangeEntity findByIdTipeExchangeAndDate(Long idTipeExchange, Date date);

    List<TrExchangeEntity> findByIdTipeExchangeAndDateBetween(Long idTipeExchange, Date datestart, Date dateend);
}
