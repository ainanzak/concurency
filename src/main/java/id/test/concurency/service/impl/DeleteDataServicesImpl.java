package id.test.concurency.service.impl;

import com.google.gson.Gson;
import id.test.concurency.dto.request.DeleteDto;
import id.test.concurency.dto.response.InputExchangeResponse;
import id.test.concurency.entity.MsTipeExchangeEntity;
import id.test.concurency.entity.TrExchangeEntity;
import id.test.concurency.helper.CustomResponseHelper;
import id.test.concurency.helper.LogUtils;
import id.test.concurency.repository.MsTipeExchangeRepository;
import id.test.concurency.repository.TrExchangeRepository;
import id.test.concurency.service.DeleteDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeleteDataServicesImpl implements DeleteDataService {

    private MsTipeExchangeRepository msTipeExchangeRepository;
    private Gson gson;
    private TrExchangeRepository trExchangeRepository;

    @Autowired
    public DeleteDataServicesImpl(MsTipeExchangeRepository msTipeExchangeRepository, Gson gson, TrExchangeRepository trExchangeRepository) {
        this.msTipeExchangeRepository = msTipeExchangeRepository;
        this.gson = gson;
        this.trExchangeRepository = trExchangeRepository;
    }

    @Override
    public ResponseEntity<Object> deleteData(DeleteDto deleteDto, LogUtils logUtils) {

        InputExchangeResponse inputRateResponse;
        try {

            // validation request input
            validate(deleteDto);

            // initiate response
            inputRateResponse = gson.fromJson(gson.toJson(deleteDto), InputExchangeResponse.class);

            // check from to in table msTipeExchange
            MsTipeExchangeEntity msTipeExchangeEntity = msTipeExchangeRepository.findByFromAndTo(deleteDto.getFrom(), deleteDto.getTo());
            if (msTipeExchangeEntity == null) {
                inputRateResponse.setStatus("please input from an to.");
                return new ResponseEntity<>(inputRateResponse, HttpStatus.CONFLICT);
            }

            List<TrExchangeEntity> exchangeEntities = trExchangeRepository.findByIdTipeExchange(msTipeExchangeEntity.getIdTipeExchange());
            for (TrExchangeEntity trExchangeEntity : exchangeEntities) {
                // delete entity in table trexchange
                trExchangeRepository.delete(trExchangeEntity);
            }

            // delete entity in table trexchange
            msTipeExchangeRepository.delete(msTipeExchangeEntity);
            inputRateResponse.setStatus("Delete Success");

        } catch (ArithmeticException arimatex) {
            logUtils.error("error InputExchangeServicesImpl rate minus", arimatex);
            return CustomResponseHelper.create(HttpStatus.BAD_REQUEST);
        } catch (NullPointerException nullex) {
            logUtils.error("error InputExchangeServicesImpl null", nullex);
            return CustomResponseHelper.create(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logUtils.error("error InputExchangeServicesImpl exception", e);
            return CustomResponseHelper.create(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(inputRateResponse, HttpStatus.OK);
    }

    @Override
    public void validate(DeleteDto deleteDto) {
        if (deleteDto.getFrom() == null || deleteDto.getFrom().equals("")) {
            throw new NullPointerException("from null");
        }
        if (deleteDto.getTo() == null || deleteDto.getTo().equals("")) {
            throw new NullPointerException("to null");
        }
    }
}
