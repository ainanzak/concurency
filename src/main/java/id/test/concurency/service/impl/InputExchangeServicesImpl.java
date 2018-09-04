package id.test.concurency.service.impl;

import com.google.gson.Gson;
import id.test.concurency.common.Constants;
import id.test.concurency.dto.request.InputDto;
import id.test.concurency.dto.response.InputExchangeResponse;
import id.test.concurency.entity.MsTipeExchangeEntity;
import id.test.concurency.helper.CustomResponseHelper;
import id.test.concurency.helper.LogUtils;
import id.test.concurency.repository.MsTipeExchangeRepository;
import id.test.concurency.service.InputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Service(Constants.SERVICE_ADD_EXCHANGE)
public class InputExchangeServicesImpl implements InputService {

    private MsTipeExchangeRepository msTipeExchangeRepository;
    private Gson gson;

    @Autowired
    public InputExchangeServicesImpl(MsTipeExchangeRepository msTipeExchangeRepository, Gson gson) {
        this.msTipeExchangeRepository = msTipeExchangeRepository;
        this.gson = gson;
    }

    @Override
    public ResponseEntity<Object> inputData(String request, LogUtils logUtils) {

        InputExchangeResponse inputRateResponse;
        try {
            InputDto inputDto = gson.fromJson(request, InputDto.class);
            // validation request input
            validate(request);

            // initiate response
            inputRateResponse = gson.fromJson(gson.toJson(inputDto), InputExchangeResponse.class);

            // check from to in table msTipeExchange
            MsTipeExchangeEntity checkExchangeEntity = msTipeExchangeRepository.findByFromAndTo(inputDto.getFrom(), inputDto.getTo());
            if (checkExchangeEntity != null) {
                inputRateResponse.setStatus("Data already added in database");
                return new ResponseEntity<>(inputRateResponse, HttpStatus.OK);
            }

            // save to database trExchangeEntity
            MsTipeExchangeEntity msTipeExchangeEntity = new MsTipeExchangeEntity();
            msTipeExchangeEntity.setTo(inputDto.getTo());
            msTipeExchangeEntity.setFrom(inputDto.getFrom());
            msTipeExchangeEntity.setDate(getServerTimestamp());

            logUtils.info(String.format("exchangeEntity > %s", msTipeExchangeEntity));
            msTipeExchangeRepository.save(msTipeExchangeEntity);

            inputRateResponse.setStatus("Success");

        } catch (ArithmeticException arimatex) {
            logUtils.error("error inputRateExchange rate minus", arimatex);
            return CustomResponseHelper.create(HttpStatus.BAD_REQUEST);
        } catch (NullPointerException nullex) {
            logUtils.error("error inputRateExchange null", nullex);
            return CustomResponseHelper.create(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            logUtils.error("error inputRateExchange exception", e);
            return CustomResponseHelper.create(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(inputRateResponse, HttpStatus.CREATED);
    }

    @Override
    public void validate(String request) throws NullPointerException {
        InputDto inputDto = gson.fromJson(request, InputDto.class);
        if (inputDto.getFrom() == null || inputDto.getFrom().equals("")) {
            throw new NullPointerException("from null");
        }
        if (inputDto.getTo() == null || inputDto.getTo().equals("")) {
            throw new NullPointerException("to null");
        }
    }

    @Override
    public Timestamp getServerTimestamp() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        return new Timestamp(date.getTime());
    }
}
