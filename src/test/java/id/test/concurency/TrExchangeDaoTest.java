package id.test.concurency;

import id.test.concurency.dto.request.InputRateDto;
import id.test.concurency.entity.MsTipeExchangeEntity;
import id.test.concurency.repository.MsTipeExchangeRepository;
import id.test.concurency.repository.TrExchangeRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrExchangeDaoTest {
    @Autowired
    MsTipeExchangeRepository msTipeExchangeRepository;

    @Autowired
    TrExchangeRepository trExchangeRepository;


    @Test
    public void saveTest(){
        InputRateDto inputRateDto = new InputRateDto("2018-08-05", 1.56, "GBP", "USD");
        MsTipeExchangeEntity msTipeExchangeEntity = msTipeExchangeRepository.findByFromAndTo(inputRateDto.getFrom(), inputRateDto.getTo());
        Assert.assertNotNull(msTipeExchangeEntity);
//        Assert.assertTrue(detailTransaction.getNominal().equals(transactionData.getNominal()));
//        Assert.assertTrue(detailTransaction.getEmail().equals(transactionData.getEmail()));
//        Assert.assertTrue(detailTransaction.getProduct().equals(transactionData.getProduct()));
//        Assert.assertTrue(detailTransaction.getTransactionId().equals(transactionData.getTransactionId()));

    }
}
